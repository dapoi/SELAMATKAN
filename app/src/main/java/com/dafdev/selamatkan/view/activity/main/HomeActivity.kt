package com.dafdev.selamatkan.view.activity.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
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
        setPermission()
    }

    private fun setBottomNav(visibility: Int) = run {
        binding.bottomNav.visibility = visibility
    }

    private fun setBottom() {
        binding.apply {
            bottomNav.setupWithNavController(findNavController(R.id.nav_host_fragment))
            findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, destination: NavDestination, _ ->
                when (destination.id) {
                    R.id.provinceFragment, R.id.cityFragment, R.id.baseHospitalListFragment, R.id.baseHospitalDetailFragment -> {
                        setBottomNav(View.GONE)
                    }
                    else -> {
                        setBottomNav(View.VISIBLE)
                    }
                }
            }
        }
    }

    private fun setPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), REQ_PERMISSION
            )
            return
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PERMISSION && resultCode == RESULT_OK) {
            return
        }
    }

    companion object {
        private const val REQ_PERMISSION = 100
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}