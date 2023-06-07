package com.aplus.movies.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.aplus.core.extensions.hide
import com.aplus.core.extensions.remove
import com.aplus.core.extensions.show
import com.aplus.movies.R
import com.aplus.feature.detail.R as resourceDetail
import com.aplus.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initActivity()
    }

    private fun initActivity() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.genresFragment,
                R.id.popularFragment,
                R.id.nowPlayingFragment,
                R.id.upcomingFragment
            )
        )
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)
        binding.navBottom.setupWithNavController(navController)
        binding.topAppBar.setOnMenuItemClickListener {
            it.onNavDestinationSelected(navController)
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.favoriteFragment -> {
                    binding.topAppBar.menu.findItem(R.id.favoriteFragment).isVisible = false
                    binding.topAppBar.menu.findItem(R.id.searchFragment).isVisible = true
                    binding.topAppBar.show()
                    binding.navBottom.hide()
                }
                R.id.searchFragment -> {
                    binding.topAppBar.remove()
                    binding.navBottom.hide()
                }
                resourceDetail.id.detailFragment -> {
                    binding.topAppBar.menu.findItem(R.id.favoriteFragment).isVisible = false
                    binding.topAppBar.menu.findItem(R.id.searchFragment).isVisible = false
                    binding.topAppBar.show()
                    binding.navBottom.remove()
                }
                else -> {
                    binding.topAppBar.menu.findItem(R.id.favoriteFragment).isVisible = true
                    binding.topAppBar.menu.findItem(R.id.searchFragment).isVisible = true
                    binding.topAppBar.show()
                    binding.navBottom.show()
                }
            }
        }
    }
}