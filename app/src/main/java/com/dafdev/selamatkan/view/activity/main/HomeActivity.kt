package com.dafdev.selamatkan.view.activity.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottom()
    }

    private fun setBottomNav(visibility: Int) = run {
        binding.bottomNav.visibility = visibility
    }

    private fun setBottom() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.apply {
            bottomNav.setupWithNavController(navController)
            navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
                when (destination.id) {
                    R.id.provinceFragment, R.id.cityFragment, R.id.baseHospitalListFragment, R.id.baseHospitalDetailFragment, R.id.provinceCovidFragment -> {
                        setBottomNav(View.GONE)
                    }
                    else -> {
                        setBottomNav(View.VISIBLE)
                    }
                }
            }
        }
    }
}