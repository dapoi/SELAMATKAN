package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.source.HealthRepository

class HospitalCovidViewModel(private val covidHospital: HealthRepository) : ViewModel() {

    fun covidHospital(provinceId: String, cityId: String) {
        covidHospital.getListCovidHospital(provinceId, cityId).asLiveData()
    }
}