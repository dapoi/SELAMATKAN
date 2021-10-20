package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.source.HealthRepository

class HospitalNonCovidViewModel(private val nonCovidHospital: HealthRepository) : ViewModel() {

    fun nonCovidHospital(provinceId: String, cityId: String) {
        nonCovidHospital.getListNonCovidHospital(provinceId, cityId).asLiveData()
    }
}