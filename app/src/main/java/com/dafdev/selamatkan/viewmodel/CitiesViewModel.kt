package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase

class CitiesViewModel(private val city: HealthUseCase) : ViewModel() {

    fun dataCity(provinceId: String) = city.getListCities(provinceId).asLiveData()
}