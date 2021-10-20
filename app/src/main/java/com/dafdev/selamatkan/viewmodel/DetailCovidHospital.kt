package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.source.HealthRepository

class DetailCovidHospital(private val detailCovidHospital: HealthRepository) : ViewModel() {

    fun detailCovidHospital(hospitalId: String) {
        detailCovidHospital.getDetailCovidHospital(hospitalId).asLiveData()
    }
}