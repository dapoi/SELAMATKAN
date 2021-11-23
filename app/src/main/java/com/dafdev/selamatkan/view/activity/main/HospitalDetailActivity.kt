package com.dafdev.selamatkan.view.activity.main

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.data.domain.model.Location
import com.dafdev.selamatkan.databinding.ActivityHospitalDetailBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.adapter.pager.HospitalDetailPagerAdapter
import com.dafdev.selamatkan.viewmodel.LocationMapHospitalViewModel
import com.dafdev.selamatkan.vo.Resource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHospitalDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            toolbar.setNavigationOnClickListener { onBackPressed() }

            val pagerAdapter = HospitalDetailPagerAdapter(
                this@HospitalDetailActivity,
                supportFragmentManager
            )
            viewPager.adapter = pagerAdapter

            tabsHospital.setupWithViewPager(viewPager, true)
        }
    }
}