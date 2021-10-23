package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dafdev.selamatkan.data.repository.HealthRepository
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class IndoDataCovidViewModel(private val covid: HealthRepository) : ViewModel() {

    fun dataCovidIndo() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val dataCovid = covid.getDataCovidIndonesia()
            emit(Resource.success(dataCovid))
            Timber.d(dataCovid.toString())
        } catch (e: Exception) {
            emit(Resource.error(null, e.message.toString()))
        }
    }
}