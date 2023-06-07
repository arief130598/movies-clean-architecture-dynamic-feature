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
import com.aplus.core.constants.DeeplinkConstant
import com.aplus.core.constants.KeyConstant
import com.aplus.core.extensions.collectLatestLifecycleFlow
import com.aplus.core.extensions.remove
import com.aplus.core.extensions.show
import com.aplus.core.utils.NavigationHelper
import com.aplus.core.utils.Status
import com.aplus.feature.home.R
import com.aplus.feature.home.databinding.FragmentGenresBinding
import com.aplus.feature.home.presentation.adapter.GenresAdapter
import com.aplus.feature.home.presentation.viewmodel.GenresViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GenresFragment : Fragment() {

    private lateinit var binding: FragmentGenresBinding
    private val viewModel : GenresViewModel by viewModels()
    @Inject lateinit var nav: NavigationHelper
    private lateinit var adapter: GenresAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_genres,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        
        adapter = GenresAdapter(
            items = listOf(),
            onClickGenres = { gotoChosenGenres(it) }
        )
        rvData.adapter = adapter
        rvData.layoutManager = LinearLayoutManager(requireContext())
                
        observer()
    }

    fun observer() = with(binding) {
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

    private fun gotoChosenGenres(genres: String) {
        nav.navigateDeeplink(
            this@GenresFragment,
            DeeplinkConstant.GENRES_MOVIES_NAVIGATION,
            Pair(KeyConstant.GENRES_KEY, genres)
        )
    }
}