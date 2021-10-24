package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dafdev.selamatkan.data.repository.HealthRepository
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class DetailCovidHospitalViewModel(private val detailCovidHospital: HealthRepository) : ViewModel() {

    fun dataDetailCovidHospital(hospitalId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val dataDetail = detailCovidHospital.getDetailCovidHospital(hospitalId)
            emit(Resource.success(dataDetail))
            Timber.d(dataDetail.toString())
        } catch (e: Exception) {
            emit(Resource.error(null, e.message.toString()))
        }
    }
}