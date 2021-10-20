package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.source.HealthRepository

class ProvinceViewModel(private val province: HealthRepository) : ViewModel() {

    fun dataProvince() {
        province.getListProvince().asLiveData()
    }

    val dataProvinces = province.getListProvince().asLiveData()
}