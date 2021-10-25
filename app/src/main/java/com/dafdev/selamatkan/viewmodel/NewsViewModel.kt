package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dafdev.selamatkan.data.repository.HealthRepository
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class NewsViewModel(private val news: HealthRepository) : ViewModel() {

    fun getNews() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val data = news.getNews()
            emit(Resource.success(data))
            Timber.d(data.toString())
        } catch (e: Exception) {
            emit(Resource.error(null, e.message.toString()))
        }
    }
}