package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase

class NewsViewModel(private val news: HealthUseCase) : ViewModel() {

    fun getNews() = news.getNews().asLiveData()
}