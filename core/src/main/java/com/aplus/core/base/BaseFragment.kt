package com.aplus.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.aplus.core.utils.NavigationHelper
import javax.inject.Inject

abstract class BaseFragment<T: ViewDataBinding>(private val layoutId: Int) : Fragment() {

    lateinit var binding: T
    @Inject
    lateinit var nav: NavigationHelper

    protected abstract fun onSetupUI()
    protected abstract fun onObserve()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onSetupUI()
        onObserve()
    }

}