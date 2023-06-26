package com.aplus.feature.home.presentation.ui

import android.content.res.Resources
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.aplus.core.base.BaseFragment
import com.aplus.core.extensions.collectLatestLifecycleFlow
import com.aplus.core.utils.ResponseResult
import com.aplus.feature.home.databinding.FragmentHomeBinding
import com.aplus.feature.home.presentation.adapter.TrendingMoviesAdapter
import com.aplus.feature.home.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun onSetupUI() = with(binding) {
        viewModel.getMovies()
    }

    override fun onObserve() {
        observeMovies()
    }

    private fun observeMovies() = with(binding) {
        collectLatestLifecycleFlow(viewModel.movies2){
            when (it) {
                is ResponseResult.Error -> {}
                ResponseResult.Loading -> {}
                is ResponseResult.Success -> {
                    slideTrending.apply {
                        clipChildren = false  // No clipping the left and right items
                        clipToPadding = false  // Show the viewpager in full width without clipping the padding
                        offscreenPageLimit = 3  // Render the left and right items
                        (getChildAt(0) as RecyclerView).overScrollMode =
                            RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
                    }

                    slideTrending.adapter = TrendingMoviesAdapter(it.data.results)

                    val compositePageTransformer = CompositePageTransformer()
                    compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
                    compositePageTransformer.addTransformer { page, position ->
                        val r = 1 - abs(position)
                        page.scaleY = (0.80f + r * 0.20f)
                    }
                    slideTrending.setPageTransformer(compositePageTransformer)
                }
            }
        }
    }
}