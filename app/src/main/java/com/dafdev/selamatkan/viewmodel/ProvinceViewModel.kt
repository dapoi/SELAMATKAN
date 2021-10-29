package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase

class ProvinceViewModel(private val province: HealthUseCase) : ViewModel() {

    fun getListProv() = province.getListProvinceHome().asLiveData()
}
