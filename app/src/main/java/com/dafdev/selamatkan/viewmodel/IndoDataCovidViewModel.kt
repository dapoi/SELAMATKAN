package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IndoDataCovidViewModel @Inject constructor(covid: HealthUseCase) : ViewModel() {

    val dataCovidIndo = covid.getDataCovidIndonesia().asLiveData()
}