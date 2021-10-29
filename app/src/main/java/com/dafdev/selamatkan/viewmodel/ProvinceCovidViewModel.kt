package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase

class ProvinceCovidViewModel(private val covid: HealthUseCase) : ViewModel() {

    fun dataCovidProv() = covid.getDataCovidProv().asLiveData()
}