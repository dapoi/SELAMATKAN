package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase

class HospitalNonCovidViewModel(private val nonCovidHospital: HealthUseCase) : ViewModel() {

    fun nonCovidHospital(provinceId: String, cityId: String) =
        nonCovidHospital.getListNonCovidHospital(provinceId, cityId).asLiveData()
}