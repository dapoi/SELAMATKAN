package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase

class LocationMapHospitalViewModel(private val location: HealthUseCase) : ViewModel() {

    fun getLocationHospital(hospitalId: String) =
        location.getLocationHospitalMap(hospitalId).asLiveData()
}