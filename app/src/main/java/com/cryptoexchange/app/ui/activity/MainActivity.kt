package com.cryptoexchange.app.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cryptoexchange.app.R
import com.cryptoexchange.app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Setup Navigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.analyticsFragment -> binding.fab.show()
                else -> binding.fab.hide()
            }
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        // Connect bottom nav with navigation controller
        bottomNav.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}