package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.source.HealthRepository

class CitiesViewModel(private val city: HealthRepository) : ViewModel() {

    fun dataCity(provinceId: String) {
        city.getListCities(provinceId).asLiveData()
    }
}