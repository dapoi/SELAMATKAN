package com.dafdev.selamatkan.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dafdev.selamatkan.data.source.HealthRepository
import com.dafdev.selamatkan.di.Injection

class ViewModelFactory private constructor(private val healthRepo: HealthRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(ProvinceViewModel::class.java) -> {
                ProvinceViewModel(healthRepo) as T
            }
            modelClass.isAssignableFrom(CitiesViewModel::class.java) -> {
                CitiesViewModel(healthRepo) as T
            }
            modelClass.isAssignableFrom(HospitalCovidViewModel::class.java) -> {
                HospitalCovidViewModel(healthRepo) as T
            }
            modelClass.isAssignableFrom(HospitalNonCovidViewModel::class.java) -> {
                HospitalNonCovidViewModel(healthRepo) as T
            }
            modelClass.isAssignableFrom(DetailCovidHospital::class.java) -> {
                DetailCovidHospital(healthRepo) as T
            }
            modelClass.isAssignableFrom(DetailNonCovidHospital::class.java) -> {
                DetailNonCovidHospital(healthRepo) as T
            }
            modelClass.isAssignableFrom(LocationMapHospitalViewModel::class.java) -> {
                LocationMapHospitalViewModel(healthRepo) as T
            }
            else -> throw Throwable("Unknown ViewModel Class: " + modelClass.name)
        }

    companion object {

        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }
}