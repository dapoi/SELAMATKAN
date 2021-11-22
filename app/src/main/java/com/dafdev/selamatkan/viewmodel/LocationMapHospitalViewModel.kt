package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationMapHospitalViewModel @Inject constructor(
    private val location: HealthUseCase
) : ViewModel() {

    fun getLocationHospital(
        hospitalId: String
    ) = location.getLocationHospitalMap(hospitalId).asLiveData()
}