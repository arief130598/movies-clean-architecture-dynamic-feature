package com.aplus.feature.search.presentation.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
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
import com.aplus.core.utils.Status
import com.aplus.feature.search.R
import com.aplus.feature.search.databinding.FragmentSearchBinding
import com.aplus.feature.search.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel : SearchViewModel by viewModels()
//    private val viewModelMovie : MovieViewModel by viewModels()
//    private lateinit var adapter: MovieAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener { findNavController().popBackStack() }
        if(binding.searchText.requestFocus()) {
            val imm =
                requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.searchText, 0)
        }

        var timer = Timer()
        val delayTime = 1000L

        binding.searchText.addTextChangedListener {
            timer.cancel()
            timer = Timer()
            timer.schedule(
                object : TimerTask() {
                    override fun run() {
                        requireActivity().runOnUiThread {
                            loadingMore = true
//                            adapter.clearData()
                            viewModel.page = 0
                            viewModel.listLoadedMovies.clear()
                            if (it.toString().isNotEmpty()) {
                                Log.d("Arief", "1")
                                viewModel.getMovies(it.toString())
                            }
                        }
                        timer.cancel()
                    }
                },
                delayTime
            )
        }

//        adapter = MovieAdapter(listOf(), this@SearchFragment)
//        binding.rvData.adapter = adapter
//        binding.rvData.layoutManager = LinearLayoutManager(requireContext())
//        if(viewModel.listLoadedMovies.isNotEmpty()) {
//            adapter.addData(viewModel.listLoadedMovies)
//            binding.mainShimmer.apply {
//                stopShimmer()
//                visibility = View.GONE
//            }
//            binding.rvData.visibility = View.VISIBLE
//        }
        binding.mainNestedScroll.setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1)
                        .measuredHeight - v.measuredHeight &&
                    scrollY > oldScrollY
                ) {
                    if (!loadingMore) {
                        Log.d("Arief", "2")
                        binding.progressBar.visibility = View.VISIBLE
                        viewModel.getMovies(binding.searchText.text.toString())
                    }
                }
            }
        }

        observer()
    }

    private fun observer(){
//        viewModelMovie.favorite.observe(viewLifecycleOwner){
//            adapter.setFavorite(it)
//        }
//
//        viewModelMovie.genres.observe(viewLifecycleOwner){
//            adapter.setGenre(it)
//        }

        viewModel.movies.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
//                    if(adapter.itemCount == 0) {
//                        binding.mainShimmer.apply {
//                            stopShimmer()
//                            visibility = View.GONE
//                            binding.rvData.visibility = View.VISIBLE
//                            adapter.addData(it.data!!)
//                            loadingMore = false
//                        }
//                    }else{
//                        binding.progressBar.visibility = View.GONE
//                        adapter.addData(it.data!!)
//                    }
                }
                Status.LOADING -> {
//                    if(adapter.itemCount == 0) {
//                        binding.mainShimmer.apply {
//                            startShimmer()
//                            visibility = View.VISIBLE
//                        }
//                    }
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

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        (requireActivity() as MainActivity).binding.topAppBar.visibility = View.GONE
//        (requireActivity() as MainActivity).binding.navBottom.visibility = View.GONE
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        (requireActivity() as MainActivity).binding.topAppBar.visibility = View.VISIBLE
//        (requireActivity() as MainActivity).binding.navBottom.visibility = View.VISIBLE
//    }
//
//    override fun onResume() {
//        super.onResume()
//        (requireActivity() as MainActivity).binding.topAppBar.visibility = View.GONE
//        (requireActivity() as MainActivity).binding.navBottom.visibility = View.GONE
//    }
//
//    override fun onPause() {
//        super.onPause()
//        (requireActivity() as MainActivity).binding.topAppBar.visibility = View.VISIBLE
//        (requireActivity() as MainActivity).binding.navBottom.visibility = View.VISIBLE
//    }
}