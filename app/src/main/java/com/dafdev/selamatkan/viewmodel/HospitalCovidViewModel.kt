package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase

class HospitalCovidViewModel(private val covidHospital: HealthUseCase) : ViewModel() {

    fun covidHospital(provinceId: String, cityId: String) =
        covidHospital.getListCovidHospital(provinceId, cityId).asLiveData()
}