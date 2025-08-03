package com.cryptoexchange.app.ui.activity

import android.content.Context
import android.content.res.Resources
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.cryptoexchange.app.R
import com.cryptoexchange.app.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //setting up theme
        val sharedPrefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isDarkMode = sharedPrefs.getBoolean("dark_mode", true)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        setContentView(binding.root)

        if (isDarkMode) {
            binding.bottomGradient.visibility = View.VISIBLE
        } else {
            binding.bottomGradient.visibility = View.GONE
        }

        val ivTheme = findViewById<ImageView>(R.id.ivTheme)

        // Set icon based on theme
        ivTheme.setImageResource(
            if (isDarkMode) R.drawable.ic_light_mode
            else R.drawable.ic_dark_mode
        )

        // Handle toggle
        ivTheme.setOnClickListener {
            val newDarkMode = !sharedPrefs.getBoolean("dark_mode", true)

            sharedPrefs.edit { putBoolean("dark_mode", newDarkMode) }

            // Trigger theme change
            AppCompatDelegate.setDefaultNightMode(
                if (newDarkMode) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

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

            //fab on press
            binding.fab.setOnClickListener {
                val options = NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
                    .setPopEnterAnim(R.anim.slide_in_left)
                    .setPopExitAnim(R.anim.slide_out_right)
                    .build()

                navController.navigate(R.id.exchangeCoinFragment, null, options)
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            this@MainActivity.window.decorView.performHapticFeedback(
                HapticFeedbackConstants.CONTEXT_CLICK,
                HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            )

            val fab = findViewById<FloatingActionButton>(R.id.fab)
            val bottomNavContainer = findViewById<LinearLayout>(R.id.bottomNavContainer)
            val topBar = findViewById<LinearLayout>(R.id.topBar)

            //setting the bg color of selected tab
            tabViews.forEach { (view, destId) ->
                view.setBackgroundResource(R.drawable.bg_tab_selector)
                view.isSelected = (destination.id == destId)
            }

            when (destination.id) {
                R.id.analyticsFragment -> {
                    fab.show()
                    bottomNavContainer.visibility = View.VISIBLE
                    topBar.visibility = View.VISIBLE

                    val params = bottomNavContainer.layoutParams as ConstraintLayout.LayoutParams
                    params.endToStart = R.id.fab
                    bottomNavContainer.layoutParams = params
                }

                R.id.exchangeCoinFragment -> {
                    fab.hide()
                    bottomNavContainer.visibility = View.GONE
                    topBar.visibility = View.GONE
                }

                else -> {
                    fab.hide()
                    bottomNavContainer.visibility = View.VISIBLE
                    topBar.visibility = View.VISIBLE

                    val params = bottomNavContainer.layoutParams as ConstraintLayout.LayoutParams
                    params.endToStart = ConstraintLayout.LayoutParams.UNSET
                    params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomNavContainer.layoutParams = params
                }
            }
        }

        //todo: used for gradient blur but currently we are using png directly as it gives better effect
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val blurView = findViewById<View>(R.id.bottomGradient)
            val blurEffect = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP)
            blurView.setRenderEffect(blurEffect)
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}