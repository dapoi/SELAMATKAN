package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.model.SearchNews
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase
import com.dafdev.selamatkan.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val newsUseCase: HealthUseCase
) : ViewModel() {
    fun getNewsSearch(query: String): LiveData<Resource<List<SearchNews>>> {
        return newsUseCase.getNewsSearch(query).asLiveData()
    }
}