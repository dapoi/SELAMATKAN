package com.dafdev.selamatkan.view.activity.core

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.data.domain.model.Location
import com.dafdev.selamatkan.databinding.ActivityMapBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.utils.HelpUtil.isOnline
import com.dafdev.selamatkan.utils.InternetReceiver
import com.dafdev.selamatkan.viewmodel.LocationMapHospitalViewModel
import com.dafdev.selamatkan.vo.Resource
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.simform.refresh.SSPullToRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.*

@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var _binding: ActivityMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var gMap: GoogleMap
    private lateinit var fusedLocation: FusedLocationProviderClient
    private lateinit var myLoc: LatLng
    private lateinit var destinationLatLng: LatLng
    private lateinit var repeatHandler: Handler
    private lateinit var repeatRunnable: Runnable

    private var myLat = 0.0
    private var myLong = 0.0
    private var destinationLat = 0.0
    private var destinationLong = 0.0

    private val permissionID = 42

    private val locationViewModel: LocationMapHospitalViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repeatHandler = Handler(Looper.getMainLooper())

        fusedLocation = LocationServices.getFusedLocationProviderClient(this)
        with(binding) {
            toolbar.setNavigationOnClickListener {
                finish()
                overridePendingTransition(R.anim.from_left, R.anim.to_right)
            }

            srlMap.apply {
                setLottieAnimation("loading.json")
                setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT)
                setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE)
                setOnRefreshListener(object : SSPullToRefreshLayout.OnRefreshListener {
                    override fun onRefresh() {
                        val check = isOnline(this@MapActivity)
                        if (check) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                setRefreshing(false)
                            }, 2000)

                            Handler(Looper.getMainLooper()).postDelayed({
                                val status = InternetReceiver()
                                status.onReceive(this@MapActivity, intent)
                            }, 2150)
                        } else {
                            nestedScrollView.visibility = View.GONE
                            clNoInternet.visibility = View.VISIBLE
                            setRefreshing(false)
                        }
                    }
                })
            }

            // show map
            val mapFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
            mapFragment.getMapAsync(this@MapActivity)

            imgRoute.setOnClickListener {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=${Constant.hospitalName}")
                ).also { startActivity(it) }
            }
            imgPhone.setOnClickListener {
                if (Constant.phoneNumber == "-") {
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
                val uriString = "http://maps.google.com/maps?daddr=${Constant.hospitalName}"
                Intent(Intent.ACTION_SEND).also {
                    it.type = "text/plain"
                    it.putExtra(Intent.EXTRA_SUBJECT, Constant.hospitalName)
                    it.putExtra(Intent.EXTRA_TEXT, uriString)
                    startActivity(Intent.createChooser(it, "Bagikan : "))
                }
            }

            tvHospitalName.text = Constant.hospitalName
            tvHospitalAddress.text = Constant.hospitalAddress
            tvHospitalPhone.text = Constant.phoneNumber
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun requestNewLocationData() {
        val locationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
            interval = 100
            fastestInterval = 3000
            numUpdates = 1
        }
        fusedLocation = LocationServices.getFusedLocationProviderClient(this)
        fusedLocation.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper()!!)
        getDistance(myLat, myLong, destinationLat, destinationLong)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val location = result.lastLocation!!
            myLong = location.longitude
            myLat = location.latitude
            myLoc = LatLng(myLat, myLong)
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), permissionID
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6372.8
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val originLat = Math.toRadians(lat1)
        val destinationLat = Math.toRadians(lat2)

        val a = sin(dLat / 2).pow(2.toDouble()) + sin(dLon / 2).pow(2.toDouble()) *
                cos(originLat) * cos(destinationLat)
        val c = 2 * asin(sqrt(a))
        val distance = earthRadius * c
        binding.tvDistance.text =
            "Perkiraan jarak dari lokasi Anda ke ${Constant.hospitalName} sekitar ${
                String.format("%.2f", distance)
            } - ${String.format("%.2f", distance * 2)} KM"
        return sqrt(distance)
    }

    override fun onMapReady(gMapReady: GoogleMap) {
        locationViewModel.getLocationHospital(Constant.hospitalId).observe(this) {
            when (it) {
                is Resource.Loading -> progressBar(true)
                is Resource.Success -> {
                    progressBar(false)
                    setUpMap(it.data, gMapReady)
                    getLocation()
                }
                is Resource.Error -> {
                    progressBar(false)
                    binding.nestedScrollView.visibility = View.GONE
                    binding.clNoInternet.visibility = View.VISIBLE
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpMap(data: Location?, gMapReady: GoogleMap) {
        if (data != null) {
            destinationLat = data.lat!!.toDouble()
            destinationLong = data.long!!.toDouble()
        }

        destinationLatLng = LatLng(destinationLat, destinationLong)
        gMap = gMapReady

        with(gMap) {
            addMarker(MarkerOptions().position(destinationLatLng).title(Constant.hospitalName))
            moveCamera(CameraUpdateFactory.newLatLng(destinationLatLng))
            animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(destinationLat, destinationLong),
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

    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocation.lastLocation.addOnCompleteListener(this) { task ->
                    val location = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        myLong = location.longitude
                        myLat = location.latitude
                        myLoc = LatLng(myLat, myLong)
                        getDistance(myLat, myLong, destinationLat, destinationLong)
                    }
                }
            } else {
                binding.tvDistance.text = "Hidupkan GPS Anda untuk melihat jarak"
                // TODO: uncomment if want to open settings to turn on location
//                Handler(Looper.getMainLooper()).postDelayed({
//                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                    startActivityForResult(intent, 2)
//                }, 3000)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onResume() {
        super.onResume()
        repeatHandler.postDelayed(Runnable { //do your function;
            getLocation()
            repeatHandler.postDelayed(repeatRunnable, 2000)
        }.also { repeatRunnable = it }, 2000)
    }

    override fun onPause() {
        super.onPause()
        repeatHandler.removeCallbacks(repeatRunnable)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.from_left, R.anim.to_right)
    }

    /**
     *
     * TODO: uncomment if want to open settings to turn on location
     *
     */
//    @Deprecated("Deprecated in Java")
//    @Suppress("DEPRECATION")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 2) {
//            checkGPS()
//        }
//    }

//    private fun checkGPS() {
//        val gps = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        if (gps.isProviderEnabled(LocationManager.GPS_PROVIDER) || gps.isProviderEnabled(
//                LocationManager.NETWORK_PROVIDER
//            )
//        ) {
//            getLocation()
//        }
//    }
}