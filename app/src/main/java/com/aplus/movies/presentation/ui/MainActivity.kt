package com.aplus.movies.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.aplus.movies.R
import com.aplus.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initActivity()
    }

    private fun initActivity() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.popularFragment, R.id.nowPlayingFragment, R.id.upcomingFragment))
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)
        binding.navBottom.setupWithNavController(navController)

        binding.topAppBar.setOnMenuItemClickListener {
            it.onNavDestinationSelected(navController)
            true
        }
    }
}