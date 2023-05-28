package com.aplus.core.utils

import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import javax.inject.Inject

class NavigationHelper @Inject constructor(){

    private val scheme = "aplus.com://"
    private val authority = "movies/"

    private fun createArguments(arguments: Array<out Pair<String, Any>>): String {
        var args = ""
        arguments.forEach {
            args += "${it.first}={${it.second}}"
        }
        return args
    }

    fun navigateDeeplink(fragment: Fragment, path: String, vararg arguments: Pair<String, Any>){
        val destination = "$scheme$authority$path?${createArguments(arguments)}".toUri()
        findNavController(fragment).navigate(destination)
    }
}
