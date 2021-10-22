package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dafdev.selamatkan.data.repository.HealthRepository
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.Dispatchers

class ProvinceViewModel(private val province: HealthRepository) : ViewModel() {

    fun getListProv() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = province.getListProvince()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message.toString()))
        }
    }
}
