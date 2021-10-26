package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dafdev.selamatkan.data.repository.HealthRepository
import com.dafdev.selamatkan.data.source.remote.response.HospitalsCovidItem
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class HospitalCovidViewModel(private val covidHospital: HealthRepository) : ViewModel() {

    fun covidHospital(
        provinceId: String,
        cityId: String
    ): LiveData<Resource<List<HospitalsCovidItem>>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val dataHospital = covidHospital.getListCovidHospital(provinceId, cityId)
                emit(Resource.success(dataHospital) as Resource<List<HospitalsCovidItem>>)
                Timber.d("$dataHospital")
            } catch (e: Exception) {
                emit(Resource.error(null, e.message.toString()))
            }
        }
    }
}