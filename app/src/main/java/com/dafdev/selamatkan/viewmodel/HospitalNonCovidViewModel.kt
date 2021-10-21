package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import com.dafdev.selamatkan.data.repository.HealthRepository

class HospitalNonCovidViewModel(private val nonCovidHospital: HealthRepository) : ViewModel() {

//    fun nonCovidHospital(provinceId: String, cityId: String) {
//        nonCovidHospital.getListNonCovidHospital(provinceId, cityId).asLiveData()
//    }
}