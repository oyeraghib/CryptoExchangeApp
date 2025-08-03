package com.cryptoexchange.app.ui.activity

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cryptoexchange.app.R
import com.cryptoexchange.app.databinding.ActivityMainBinding
import com.cryptoexchange.app.databinding.CustomBottomNavBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val tabViews = listOf(
            binding.customBottomNav.navAnalytics to R.id.analyticsFragment,
            binding.customBottomNav.navExchange to R.id.exchangeFragment,
            binding.customBottomNav.navRecord to R.id.recordFragment,
            binding.customBottomNav.navWallet to R.id.walletFragment
        )

        tabViews.forEach { (view, destinationId) ->
            view.setOnClickListener {
                navController.navigate(destinationId)
            }

            binding.fab.setOnClickListener {
                navController.navigate(R.id.globalExchangeCoinFragment)
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            tabViews.forEach { (view, id) ->
                view.isSelected = (destination.id == id)
            }

            if (destination.id == R.id.analyticsFragment) {
                binding.fab.show()
            } else {
//                binding.fab.hide()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val blurView = findViewById<View>(R.id.bottomGradient)
            val blurEffect = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP)
            blurView.setRenderEffect(blurEffect)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
    }
}