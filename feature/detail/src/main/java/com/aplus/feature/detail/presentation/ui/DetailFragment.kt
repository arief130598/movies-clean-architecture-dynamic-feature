package com.aplus.feature.detail.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplus.core.extensions.collectLatestLifecycleFlow
import com.aplus.core.extensions.deserialize
import com.aplus.core.extensions.removeFirstAndLast
import com.aplus.core.extensions.show
import com.aplus.core.utils.Status
import com.aplus.domain.model.Movies
import com.aplus.feature.detail.R
import com.aplus.feature.detail.databinding.FragmentDetailBinding
import com.aplus.feature.detail.presentation.adapter.SimilarAdapter
import com.aplus.feature.detail.presentation.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import com.aplus.common.R as resourceCommon


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel : DetailViewModel by viewModels()
    private lateinit var adapter: SimilarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SimilarAdapter(listOf(), this@DetailFragment)
        binding.rvData.adapter = adapter
        binding.rvData.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val moviesArgs = DetailFragmentArgs.fromBundle(requireArguments()).moviesArg
        val movies = moviesArgs.removeFirstAndLast().deserialize<Movies>()
        setupUI(movies!!)
        observer(movies)
    }

    fun setupUI(item: Movies) = with(binding) {
        if (!item.backdrop_path.isNullOrEmpty()) {
            Glide.with(requireContext())
                .load(requireContext().getString(R.string.background_image_url) + item.backdrop_path)
                .error(resourceCommon.drawable.no_image)
                .into(backgroundPoster)
        }
        if (!item.poster_path.isNullOrEmpty()) {
            Glide.with(requireContext())
                .load(requireContext().getString(resourceCommon.string.image_url) + item.poster_path)
                .error(resourceCommon.drawable.no_image)
                .into(poster)
        }
        title.text = item.title
        release.text = item.release_date
        popularity.text = item.popularity.toString()
        rating.text = item.vote_average.toString()
        ratingTotal.text = item.vote_count.toString()
        language.text = item.original_language
        overview.text = item.overview

        viewModel.getSimilar(item.id)
        viewModel.getVideos(item.id)
    }

    private fun convertGenres(data: List<Int>): String{
        var genres = ""
        return if(data.isNotEmpty()) {
            data.forEach {
                val item = viewModel.genres.value.filter { x -> x.id == it }
                if (item.isNotEmpty()) {
                    genres += "${item[0].name}, "
                }
            }
            if(genres.length > 2) {
                genres.substring(0, genres.length-2)
            }else{
                genres
            }
        }else{
            genres
        }
    }

    private fun observer(item: Movies) = with(binding) {
        collectLatestLifecycleFlow(viewModel.genres){
            genres.text = "Genres : ${convertGenres(item.genre_ids)}"
        }
        collectLatestLifecycleFlow(viewModel.favorit){
            if(viewModel.favorit.value.any { it.id == item.id }){
                favorite.setImageResource(resourceCommon.drawable.ic_favorite_32)
            }else{
                favorite.setImageResource(resourceCommon.drawable.ic_favorite_border_32)
            }
            favorite.setOnClickListener {
                if(viewModel.favorit.value.any { it.id == item.id }){
                    favorite.setImageResource(resourceCommon.drawable.ic_favorite_32)
                }else{
                    favorite.setImageResource(resourceCommon.drawable.ic_favorite_border_32)
                }
                viewModel.insertDeleteFavorite(item)
            }
        }
        collectLatestLifecycleFlow(viewModel.movies){
            when (it.status) {
                Status.SUCCESS -> {
                    mainShimmer.apply {
                        stopShimmer()
                        visibility = View.GONE
                        rvData.visibility = View.VISIBLE
                        adapter.setData(it.data!!)
                    }
                }
                Status.LOADING -> {
                    mainShimmer.apply {
                        rvData.visibility = View.GONE
                        startShimmer()
                        visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    mainShimmer.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        collectLatestLifecycleFlow(viewModel.videos){
            if(it.status == Status.SUCCESS && !it.data.isNullOrEmpty()){
                videoTrailer.show()
                viewLifecycleOwner.lifecycle.addObserver(videoTrailer)
                videoTrailer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        val videoId = it.data!!.first().key
                        youTubePlayer.cueVideo(videoId, 0f)
                    }
                })
            }
        }
    }
}