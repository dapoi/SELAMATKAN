package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dafdev.selamatkan.data.repository.HealthRepository
import com.dafdev.selamatkan.data.source.response.HospitalsNonCovidItem
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class HospitalNonCovidViewModel(private val nonCovidHospital: HealthRepository) : ViewModel() {

    fun nonCovidHospital(
        provinceId: String,
        cityId: String
    ): LiveData<Resource<List<HospitalsNonCovidItem>>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val dataHospital = nonCovidHospital.getListNonCovidHospital(provinceId, cityId)
                emit(Resource.success(dataHospital))
                Timber.d("$dataHospital")
            } catch (e: Exception) {
                emit(Resource.error(null, e.message.toString()))
            }
        }
    }
}