package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dafdev.selamatkan.data.repository.HealthRepository
import com.dafdev.selamatkan.data.source.response.CitiesItem
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.Dispatchers

class CitiesViewModel(private val city: HealthRepository) : ViewModel() {

    fun dataCity(provinceId: String): LiveData<Resource<List<CitiesItem>>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = city.getListCities(provinceId)) as Resource<List<CitiesItem>>)
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message.toString()))
            }
        }
    }
}