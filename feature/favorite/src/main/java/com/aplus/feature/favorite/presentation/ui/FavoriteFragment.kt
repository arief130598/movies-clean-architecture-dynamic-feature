package com.aplus.feature.favorite.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplus.common.presentation.adapter.MovieAdapter
import com.aplus.core.constants.DeeplinkConstant
import com.aplus.core.constants.KeyConstant
import com.aplus.core.extensions.collectLatestLifecycleFlow
import com.aplus.core.extensions.remove
import com.aplus.core.extensions.serialize
import com.aplus.core.extensions.show
import com.aplus.core.utils.NavigationHelper
import com.aplus.feature.favorite.R
import com.aplus.feature.favorite.databinding.FragmentFavoriteBinding
import com.aplus.feature.favorite.presentation.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel : FavoriteViewModel by viewModels()
    @Inject lateinit var nav: NavigationHelper
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorite,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        mainShimmer.apply {
            startShimmer()
            visibility = View.VISIBLE
        }
        adapter = MovieAdapter(
            items = listOf(),
            onClickFavorite = { movies, i ->
                viewModel.insertDeleteFavorite(movies, i)
            },
            onClickMovies = {
                val args = it.serialize()
                nav.navigateDeeplink(
                    this@FavoriteFragment,
                    DeeplinkConstant.DETAIL_NAVIGATION,
                    Pair(KeyConstant.MOVIES_KEY, args)
                )
            }
        )
        rvData.adapter = adapter
        rvData.layoutManager = LinearLayoutManager(requireContext())

        observer()
    }

    private fun observer() = with(viewModel) {
        collectLatestLifecycleFlow(genres){
            adapter.setGenre(it)
            viewModel.getFavorite()
        }
        collectLatestLifecycleFlow(favorit){
            binding.apply {
                mainShimmer.stopShimmer()
                mainShimmer.remove()
                if(it.isNotEmpty()) adapter.setFavorite(it)
                rvData.show()
                adapter.clearData()
                adapter.addData(it)
            }
        }
        collectLatestLifecycleFlow(movies){ data ->
            if(data.isNotEmpty()) data.firstNotNullOf { adapter.removeData(it.value, it.key) }
        }
    }
}