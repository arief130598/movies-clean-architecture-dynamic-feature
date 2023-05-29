package com.aplus.feature.search.presentation.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplus.common.presentation.adapter.MovieAdapter
import com.aplus.core.constants.DeeplinkConstant
import com.aplus.core.constants.KeyConstant
import com.aplus.core.extensions.collectLatestLifecycleFlow
import com.aplus.core.extensions.serialize
import com.aplus.core.utils.NavigationHelper
import com.aplus.core.utils.Status
import com.aplus.feature.search.R
import com.aplus.feature.search.databinding.FragmentSearchBinding
import com.aplus.feature.search.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel : SearchViewModel by viewModels()
    @Inject lateinit var nav: NavigationHelper
    private lateinit var adapter: MovieAdapter
    private var loadingMore = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
                inflater,
        R.layout.fragment_search,
        container,
        false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        ivBack.setOnClickListener { findNavController().popBackStack() }
        if(searchText.requestFocus()) {
            val imm =
                requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchText, 0)
        }

        var timer = Timer()
        val delayTime = 1000L

        searchText.addTextChangedListener {
            timer.cancel()
            timer = Timer()
            timer.schedule(
                object : TimerTask() {
                    override fun run() {
                        requireActivity().runOnUiThread {
                            loadingMore = true
                            adapter.clearData()
                            viewModel.page = 0
                            viewModel.listLoadedMovies.clear()
                            if (it.toString().isNotEmpty()) {
                                viewModel.getMovies(it.toString())
                            }
                        }
                        timer.cancel()
                    }
                },
                delayTime
            )
        }

        adapter = MovieAdapter(
            items = listOf(),
            onClickFavorite =  { it, _ -> viewModel.insertDeleteFavorite(it) },
            onClickMovies = {
                val args = it.serialize()
                nav.navigateDeeplink(
                    this@SearchFragment,
                    DeeplinkConstant.DETAIL_NAVIGATION,
                    Pair(KeyConstant.MOVIES_KEY, args)
                )
            }
        )
        rvData.adapter = adapter
        rvData.layoutManager = LinearLayoutManager(requireContext())
        if(viewModel.listLoadedMovies.isNotEmpty()) {
            adapter.addData(viewModel.listLoadedMovies)
            mainShimmer.apply {
                stopShimmer()
                visibility = View.GONE
            }
            rvData.visibility = View.VISIBLE
        }
        mainNestedScroll.setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1)
                        .measuredHeight - v.measuredHeight &&
                    scrollY > oldScrollY
                ) {
                    if (!loadingMore) {
                        progressBar.visibility = View.VISIBLE
                        viewModel.getMovies(searchText.text.toString())
                    }
                }
            }
        }

        observer()
    }

    private fun observer() = with(viewModel) {
        collectLatestLifecycleFlow(genres){
            adapter.setGenre(it)
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
                                loadingMore = false
                            }
                        }else{
                            progressBar.visibility = View.GONE
                            adapter.addData(it.data!!)
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