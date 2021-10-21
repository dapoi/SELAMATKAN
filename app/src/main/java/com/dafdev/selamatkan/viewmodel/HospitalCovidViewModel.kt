package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import com.dafdev.selamatkan.data.repository.HealthRepository

class HospitalCovidViewModel(private val covidHospital: HealthRepository) : ViewModel() {

//    fun covidHospital(provinceId: String, cityId: String) {
//        covidHospital.getListCovidHospital(provinceId, cityId).asLiveData()
//    }
}