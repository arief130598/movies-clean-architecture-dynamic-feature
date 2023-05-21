package com.aplus.feature.favorite.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aplus.feature.favorite.R
import com.aplus.feature.favorite.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
//    private val viewModelMovie : MovieViewModel by viewModels()
//    private lateinit var adapter: MovieAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        adapter = MovieAdapter(listOf(), this@FavoriteFragment)
//        binding.rvData.adapter = adapter
//        binding.rvData.layoutManager = LinearLayoutManager(requireContext())

        observer()
    }

    private fun observer(){
//        viewModelMovie.favorite.observe(viewLifecycleOwner){
//            adapter.setData(it)
//            adapter.setFavorite(it)
//        }
//
//        viewModelMovie.genres.observe(viewLifecycleOwner){
//            adapter.setGenre(it)
//        }
    }

//    override fun onResume() {
//        super.onResume()
//        (requireActivity() as MainActivity).binding.topAppBar.menu.findItem(R.id.favoriteFragment).isVisible = false
//        (requireActivity() as MainActivity).binding.navBottom.visibility = View.INVISIBLE
//    }
//
//    override fun onPause() {
//        super.onPause()
//        (requireActivity() as MainActivity).binding.topAppBar.menu.findItem(R.id.favoriteFragment).isVisible = true
//        (requireActivity() as MainActivity).binding.navBottom.visibility = View.VISIBLE
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        (requireActivity() as MainActivity).binding.topAppBar.menu.findItem(R.id.favoriteFragment).isVisible = false
//        (requireActivity() as MainActivity).binding.navBottom.visibility = View.INVISIBLE
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        (requireActivity() as MainActivity).binding.topAppBar.menu.findItem(R.id.favoriteFragment).isVisible = true
//        (requireActivity() as MainActivity).binding.navBottom.visibility = View.VISIBLE
//    }
}