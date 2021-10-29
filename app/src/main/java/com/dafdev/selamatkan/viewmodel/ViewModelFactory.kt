package com.dafdev.selamatkan.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase
import com.dafdev.selamatkan.di.Injection

class ViewModelFactory(private val healthUseCase: HealthUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(IndoDataCovidViewModel::class.java) -> {
                IndoDataCovidViewModel(healthUseCase) as T
            }
            modelClass.isAssignableFrom(ProvinceCovidViewModel::class.java) -> {
                ProvinceCovidViewModel(healthUseCase) as T
            }
            modelClass.isAssignableFrom(ProvinceInsideViewModel::class.java) -> {
                ProvinceInsideViewModel(healthUseCase) as T
            }
            modelClass.isAssignableFrom(ProvinceViewModel::class.java) -> {
                ProvinceViewModel(healthUseCase) as T
            }
            modelClass.isAssignableFrom(CitiesViewModel::class.java) -> {
                CitiesViewModel(healthUseCase) as T
            }
            modelClass.isAssignableFrom(HospitalCovidViewModel::class.java) -> {
                HospitalCovidViewModel(healthUseCase) as T
            }
            modelClass.isAssignableFrom(HospitalNonCovidViewModel::class.java) -> {
                HospitalNonCovidViewModel(healthUseCase) as T
            }
            modelClass.isAssignableFrom(DetailCovidHospitalViewModel::class.java) -> {
                DetailCovidHospitalViewModel(healthUseCase) as T
            }
            modelClass.isAssignableFrom(DetailNonCovidHospitalViewModel::class.java) -> {
                DetailNonCovidHospitalViewModel(healthUseCase) as T
            }
            modelClass.isAssignableFrom(LocationMapHospitalViewModel::class.java) -> {
                LocationMapHospitalViewModel(healthUseCase) as T
            }
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(healthUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel Class: " + modelClass.name)
        }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideHealthUseCase(context)
                )
            }
    }
}