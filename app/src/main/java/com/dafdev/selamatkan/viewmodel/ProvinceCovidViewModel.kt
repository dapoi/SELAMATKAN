package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProvinceCovidViewModel @Inject constructor(
    private val covid: HealthUseCase
) : ViewModel() {

    fun dataCovidProv() = covid.getDataCovidProv().asLiveData()
}