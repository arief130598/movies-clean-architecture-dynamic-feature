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
import androidx.recyclerview.widget.RecyclerView
import com.aplus.common.utils.MoviesHelper
import com.aplus.core.extensions.collectLatestLifecycleFlow
import com.aplus.core.extensions.deserialize
import com.aplus.core.extensions.remove
import com.aplus.core.extensions.removeFirstAndLast
import com.aplus.core.extensions.show
import com.aplus.core.utils.Status
import com.aplus.domain.model.Movies
import com.aplus.feature.detail.R
import com.aplus.feature.detail.databinding.FragmentDetailBinding
import com.aplus.feature.detail.presentation.adapter.ReviewAdapter
import com.aplus.feature.detail.presentation.adapter.SimilarAdapter
import com.aplus.feature.detail.presentation.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.aplus.common.R as resourceCommon


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel : DetailViewModel by viewModels()
    private lateinit var similarAdapter: SimilarAdapter
    private lateinit var reviewAdapter: ReviewAdapter
    @Inject lateinit var moviesHelper: MoviesHelper
    private lateinit var youtubePlayerHelper: YouTubePlayer
    private var initializeVideo = false
    private var loadingMore = false

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

        val moviesArgs = DetailFragmentArgs.fromBundle(requireArguments()).moviesArg
        val movies = moviesArgs.removeFirstAndLast().deserialize<Movies>()

        similarAdapter = SimilarAdapter(listOf(), this@DetailFragment)
        binding.rvData.adapter = similarAdapter
        binding.rvData.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        reviewAdapter = ReviewAdapter(listOf(), this@DetailFragment)
        binding.rvReviews.adapter = reviewAdapter
        binding.rvReviews.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvReviews.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = binding.rvReviews.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == reviewAdapter.itemCount - 1){
                    if (!loadingMore) {
                        movies?.let { viewModel.getReview(movies.id) }
                    }
                }
            }
        })

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

        videoTrailer.remove()
        viewModel.getVideos(item.id)
        viewModel.getSimilar(item.id)
        rvReviews.remove()
        viewModel.pageReview = 0
        reviewAdapter.clearData()
        viewModel.getReview(item.id)
    }

    private fun observer(item: Movies) = with(binding) {
        observeGenres(item)
        observeFavorit(item)
        observeSimilar()
        observeVideos()
        observeReviews()
    }

    private fun observeGenres(item: Movies) = with(binding) {
        collectLatestLifecycleFlow(viewModel.genres) {
            genres.text =
                "Genres : ${moviesHelper.convertGenres(item.genre_ids, viewModel.genres.value)}"
        }
    }

    private fun observeFavorit(item: Movies) = with(binding) {
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
    }

    private fun observeSimilar() = with(binding) {
        collectLatestLifecycleFlow(viewModel.movies){
            when (it.status) {
                Status.SUCCESS -> {
                    mainShimmer.apply {
                        stopShimmer()
                        visibility = View.GONE
                        rvData.visibility = View.VISIBLE
                        similarAdapter.setData(it.data!!)
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
    }

    private fun observeVideos() = with(binding) {
        collectLatestLifecycleFlow(viewModel.videos){
            if(it.status == Status.SUCCESS && !it.data.isNullOrEmpty()){
                videoTrailer.show()
                val videoId = it.data!!.first().key
                if(!initializeVideo){
                    viewLifecycleOwner.lifecycle.addObserver(videoTrailer)
                    videoTrailer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.cueVideo(videoId, 0f)
                            youtubePlayerHelper = youTubePlayer
                        }
                    })
                    initializeVideo = true
                } else youtubePlayerHelper.cueVideo(videoId, 0f)
            }
        }
    }

    private fun observeReviews() = with(binding) {
        collectLatestLifecycleFlow(viewModel.reviews){
            when (it.status) {
                Status.SUCCESS -> {
                    if(reviewAdapter.itemCount == 0) {
                        reviewShimmer.stopShimmer()
                        reviewShimmer.remove()
                        rvReviews.show()
                        reviewAdapter.addData(it.data!!)
                    }else{
                        if(!it.data.isNullOrEmpty()){
                            rvReviews.show()
                            reviewAdapter.addData(it.data!!)
                        }
                    }
                }
                Status.LOADING -> {
                    reviewShimmer.apply {
                        rvReviews.visibility = View.GONE
                        startShimmer()
                        visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    reviewShimmer.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }
                }
            }
        }
    }
}