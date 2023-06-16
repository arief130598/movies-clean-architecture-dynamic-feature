package com.aplus.feature.home.presentation.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aplus.core.base.BaseFragment
import com.aplus.feature.home.R
import com.aplus.feature.home.databinding.FragmentHomeBinding
import com.aplus.feature.home.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onSetupUI() {
        TODO("Not yet implemented")
    }

    override fun onObserve() {
        TODO("Not yet implemented")
    }

}