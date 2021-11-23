package com.dafdev.selamatkan.view.activity.main

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.data.domain.model.Location
import com.dafdev.selamatkan.databinding.ActivityMapBinding
import com.dafdev.selamatkan.utils.Constant
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
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private lateinit var gMap: GoogleMap

    private var latitudeString: String? = null
    private var longitudeString: String? = null

    private val locationViewModel: LocationMapHospitalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            toolbar.setNavigationOnClickListener { onBackPressed() }

            // show map
            val mapFragment = supportFragmentManager.findFragmentById(R.id.fragment_map_hospital)
                    as SupportMapFragment
            mapFragment.getMapAsync(this@MapActivity)

            imgRoute.setOnClickListener {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=$latitudeString,$longitudeString")
                ).also { startActivity(it) }
            }
            imgPhone.setOnClickListener {
                if (Constant.phoneNumber == "") {
                    Toast.makeText(
                        this@MapActivity,
                        "Maaf, nomor rumah sakit ini tidak diketahui",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Constant.phoneNumber)).also {
                        startActivity(it)
                    }
                }
            }
            imgShare.setOnClickListener {
                val uriString = "http://maps.google.com/maps?saddr=$latitudeString,$longitudeString"
                Intent(Intent.ACTION_SEND).also {
                    it.type = "text/plain"
                    it.putExtra(Intent.EXTRA_SUBJECT, Constant.hospitalName)
                    it.putExtra(Intent.EXTRA_TEXT, uriString)
                    startActivity(Intent.createChooser(it, "Bagikan : "))
                }
            }

            tvHospitalName.text = Constant.hospitalName
            tvHospitalAddress.text = Constant.hospitalAddress

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
        locationViewModel.getLocationHospital(Constant.hospitalId).observe(this, {
            when (it) {
                is Resource.Loading -> progressBar(true)
                is Resource.Success -> {
                    progressBar(false)
                    setUpMap(it.data, gMapReady)
                }
                is Resource.Error -> {
                    progressBar(false)
                    Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setUpMap(data: Location?, gMapReady: GoogleMap) {
        latitudeString = data?.lat.toString()
        longitudeString = data?.long.toString()

        gMap = gMapReady

        val latLong = LatLng(latitudeString!!.toDouble(), longitudeString!!.toDouble())
        with(gMap) {
            addMarker(MarkerOptions().position(latLong).title(Constant.hospitalName)).also {
                it?.showInfoWindow()
            }
            moveCamera(CameraUpdateFactory.newLatLng(latLong))
            animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    latLong,
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