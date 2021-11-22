package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HospitalCovidViewModel @Inject constructor(
    private val covidHospital: HealthUseCase
) : ViewModel() {

    fun covidHospital(
        provinceId: String,
        cityId: String
    ) = covidHospital.getListCovidHospital(provinceId, cityId).asLiveData()
}