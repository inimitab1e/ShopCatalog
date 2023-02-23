package com.example.shopcatalog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.ActivityMainBinding
import com.example.shopcatalog.extensions.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var navConrtoller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavigation()
        setupBottomNavigationCartBadge()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        navConrtoller = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navConrtoller)

        //list of fragment id's, where bottomNav must not be visible
        val onInvisibleBottomNavFragments =
            listOf(R.id.loginFragment, R.id.launchAppFragment, R.id.registerFragment)

        navConrtoller.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in onInvisibleBottomNavFragments) {
                binding.bottomNavigationView.isGone = true
            } else {
                binding.bottomNavigationView.isVisible = true
            }
        }
    }

    private fun setupBottomNavigationCartBadge() {
        mainActivityViewModel.catalogItemCount.onEach { cartItems ->
            if (cartItems == cartSizeDefaultValue) {
                binding.bottomNavigationView.removeBadge(R.id.cartFragment)
            } else {
                binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment).number = cartItems
            }
        }.launchWhenStarted(lifecycleScope)
    }

    companion object {
        const val cartSizeDefaultValue = 0
    }
}