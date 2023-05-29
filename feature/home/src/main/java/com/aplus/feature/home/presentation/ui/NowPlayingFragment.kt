package com.aplus.feature.home.presentation.ui

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
import com.aplus.common.presentation.adapter.MovieAdapter
import com.aplus.core.constants.DeeplinkConstant
import com.aplus.core.constants.KeyConstant
import com.aplus.core.extensions.collectLatestLifecycleFlow
import com.aplus.core.extensions.serialize
import com.aplus.core.utils.NavigationHelper
import com.aplus.core.utils.Status
import com.aplus.feature.home.R
import com.aplus.feature.home.databinding.FragmentNowPlayingBinding
import com.aplus.feature.home.presentation.viewmodel.NowPlayingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NowPlayingFragment : Fragment() {

    private lateinit var binding: FragmentNowPlayingBinding
    private val viewModel : NowPlayingViewModel by viewModels()
    @Inject lateinit var nav: NavigationHelper
    private lateinit var adapter: MovieAdapter
    private var loadingMore = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_now_playing,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding){
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter(
            items = listOf(),
            onClickFavorite =  { it, _ -> viewModel.insertDeleteFavorite(it) },
            onClickMovies = {
                val args = it.serialize()
                nav.navigateDeeplink(
                    this@NowPlayingFragment,
                    DeeplinkConstant.DETAIL_NAVIGATION,
                    Pair(KeyConstant.MOVIES_KEY, args)
                )
            }
        )
        rvData.adapter = adapter
        rvData.layoutManager = LinearLayoutManager(requireContext())
        if(viewModel.listLoadedMovies.isNotEmpty()) {
            adapter.addData(viewModel.listLoadedMovies)
            rvData.scrollToPosition(viewModel.lastPositionAdapter)
            mainShimmer.apply {
                stopShimmer()
                visibility = View.GONE
            }
            rvData.visibility = View.VISIBLE
        }
        rvData.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = rvData.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1){
                    if (!loadingMore) {
                        progressBar.visibility = View.VISIBLE
                        viewModel.getMovies()
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                viewModel.setLastPosition(layoutManager.findFirstVisibleItemPosition())
            }
        })

        observer()
    }

    private fun observer() = with(viewModel) {
        collectLatestLifecycleFlow(genres){
            adapter.setGenre(it)
            getMovies()
        }
        collectLatestLifecycleFlow(favorit){
            if(it.isNotEmpty()) adapter.setFavorite(it)
        }

        collectLatestLifecycleFlow(movies){
            when (it.status) {
                Status.SUCCESS -> {
                    binding.apply {
                        if(adapter.itemCount == 0) {
                            mainShimmer.apply {
                                stopShimmer()
                                visibility = View.GONE
                                rvData.visibility = View.VISIBLE
                                adapter.addData(it.data!!)
                            }
                        }else{
                            progressBar.visibility = View.GONE
                            adapter.addData(it.data!!)
                            rvData.scrollToPosition(viewModel.lastPositionAdapter)
                        }
                    }
                }
                Status.LOADING -> {
                    if(adapter.itemCount == 0) {
                        binding.mainShimmer.apply {
                            startShimmer()
                            visibility = View.VISIBLE
                        }
                    }
                }
                Status.ERROR -> {
                    binding.mainShimmer.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}