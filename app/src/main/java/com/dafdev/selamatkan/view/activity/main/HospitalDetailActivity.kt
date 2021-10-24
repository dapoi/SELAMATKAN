package com.dafdev.selamatkan.view.activity.main

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.data.source.RemoteDataSource
import com.dafdev.selamatkan.data.source.network.ApiConfig
import com.dafdev.selamatkan.data.source.response.DataMapHospital
import com.dafdev.selamatkan.databinding.ActivityHospitalDetailBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.adapter.HospitalDetailPagerAdapter
import com.dafdev.selamatkan.viewmodel.LocationMapHospitalViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class HospitalDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityHospitalDetailBinding
    private lateinit var gMap: GoogleMap
    private lateinit var locationViewModel: LocationMapHospitalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            toolbar.setNavigationOnClickListener { onBackPressed() }

            // show map
            val mapFragment = supportFragmentManager.findFragmentById(R.id.fragment_map_hospital)
                    as SupportMapFragment
            mapFragment.getMapAsync(this@HospitalDetailActivity)

            val pagerAdapter = HospitalDetailPagerAdapter(
                this@HospitalDetailActivity,
                supportFragmentManager
            )
            viewPager.adapter = pagerAdapter

            tabsHospital.setupWithViewPager(viewPager, true)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_GRANTED) {
                finish()
                startActivity(intent)
            }
        }
    }

    override fun onMapReady(gMapReady: GoogleMap) {

        val factory = ViewModelFactory(RemoteDataSource(ApiConfig.provideApiHospital()))
        locationViewModel = ViewModelProvider(
            this,
            factory
        )[LocationMapHospitalViewModel::class.java]
        locationViewModel.getLocationHospital(Constant.hospitalId).observe(this, {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> progressBar(true)
                    Status.SUCCESS -> {
                        progressBar(false)
                        setUpMap(resource.data, gMapReady)
                    }
                    Status.ERROR -> {
                        progressBar(false)
                        Timber.e("error " + resource.message.toString())
                        Snackbar.make(binding.root, "error", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun setUpMap(data: DataMapHospital?, gMapReady: GoogleMap) {
        val latitudeString = data?.lat
        val longitudeString = data?.long

        gMap = gMapReady
        val latLong = LatLng(latitudeString?.toDouble()!!, longitudeString?.toDouble()!!)
        with(gMap) {
            addMarker(MarkerOptions().position(latLong).title(Constant.hospitalName))
            moveCamera(CameraUpdateFactory.newLatLng(latLong))
            animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(latitudeString.toDouble(), longitudeString.toDouble()),
                    14f
                )
            )
            uiSettings.setAllGesturesEnabled(true)
            uiSettings.isZoomGesturesEnabled = true
            isTrafficEnabled = true
        }
    }

    private fun progressBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}