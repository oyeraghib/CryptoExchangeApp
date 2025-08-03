package com.cryptoexchange.app.ui.activity

import android.content.res.Resources
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.cryptoexchange.app.R
import com.cryptoexchange.app.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
            val fab = findViewById<FloatingActionButton>(R.id.fab)
            val bottomNavContainer = findViewById<LinearLayout>(R.id.bottomNavContainer)
            val topBar = findViewById<LinearLayout>(R.id.topBar)

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val blurView = findViewById<View>(R.id.bottomGradient)
            val blurEffect = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP)
            blurView.setRenderEffect(blurEffect)
        }

    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun onDestroy() {
        super.onDestroy()
    }
}