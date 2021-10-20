package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.source.HealthRepository

class DetailNonCovidHospital(private val detailNonCovidHospital: HealthRepository) : ViewModel() {

    fun detailNonCovidHospital(hospitalId: String) {
        detailNonCovidHospital.getDetailNonCovidHospital(hospitalId).asLiveData()
    }
}