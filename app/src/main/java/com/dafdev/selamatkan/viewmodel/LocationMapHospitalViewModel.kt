package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.source.HealthRepository

class LocationMapHospitalViewModel(private val location: HealthRepository) : ViewModel() {

    fun locationHospital(hospitalId: String) {
        location.getLocationHospital(hospitalId).asLiveData()
    }
}