package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.model.News
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase

class NewsViewModel(private val newsUseCase: HealthUseCase) : ViewModel() {

    fun getNews() = newsUseCase.getNews().asLiveData()

    fun updateFavNews(news: News, statusFav: Boolean) =
        newsUseCase.updateFavNews(news, statusFav)
}