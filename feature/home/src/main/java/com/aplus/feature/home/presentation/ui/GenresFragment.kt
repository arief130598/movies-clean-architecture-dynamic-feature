package com.aplus.feature.home.presentation.ui

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.aplus.core.base.BaseFragment
import com.aplus.core.extensions.collectLatestLifecycleFlow
import com.aplus.core.extensions.remove
import com.aplus.core.extensions.setLinearAdapter
import com.aplus.core.extensions.show
import com.aplus.core.utils.Status
import com.aplus.feature.home.R
import com.aplus.feature.home.databinding.FragmentGenresBinding
import com.aplus.feature.home.presentation.adapter.GenresAdapter
import com.aplus.feature.home.presentation.navigation.gotoChosenGenres
import com.aplus.feature.home.presentation.viewmodel.GenresViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenresFragment :
    BaseFragment<FragmentGenresBinding>(R.layout.fragment_genres) {

    private val viewModel: GenresViewModel by viewModels()
    private lateinit var adapter: GenresAdapter

    override fun onSetupUI() = with(binding) {
        adapter = GenresAdapter(
            items = listOf(),
            onClickGenres = { gotoChosenGenres(it) }
        )
        rvData.setLinearAdapter(adapter)
    }

    override fun onObserve() {
        observeGenres()
    }

    private fun observeGenres() = with(binding) {
        collectLatestLifecycleFlow(viewModel.genres) {
            when (it.status) {
                Status.SUCCESS -> {
                    mainShimmer.stopShimmer()
                    mainShimmer.remove()
                    rvData.visibility = View.VISIBLE
                    it.data?.let { data -> adapter.setData(data) }
                }
                Status.LOADING -> {
                    if(adapter.itemCount == 0) {
                        mainShimmer.apply {
                            startShimmer()
                            show()
                        }
                    }
                }
                Status.ERROR -> {
                    mainShimmer.apply {
                        stopShimmer()
                        remove()
                    }
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}