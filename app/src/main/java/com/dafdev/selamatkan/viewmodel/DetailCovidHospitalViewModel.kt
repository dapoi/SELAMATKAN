package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase

class DetailCovidHospitalViewModel(private val detailCovidHospital: HealthUseCase) : ViewModel() {

    fun dataDetailCovidHospital(hospitalId: String) =
        detailCovidHospital.getDetailCovidHospital(hospitalId).asLiveData()
}