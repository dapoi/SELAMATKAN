package com.dafdev.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dafdev.selamatkan.data.repository.HealthRepository
import com.dafdev.selamatkan.data.source.remote.RemoteDataSource

class ViewModelFactory(private val remoteDataSource: RemoteDataSource) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(IndoDataCovidViewModel::class.java) -> {
                IndoDataCovidViewModel(HealthRepository(remoteDataSource)) as T
            }
            modelClass.isAssignableFrom(ProvinceCovidViewModel::class.java) -> {
                ProvinceCovidViewModel(HealthRepository(remoteDataSource)) as T
            }
            modelClass.isAssignableFrom(ProvinceViewModel::class.java) -> {
                ProvinceViewModel(HealthRepository(remoteDataSource)) as T
            }
            modelClass.isAssignableFrom(CitiesViewModel::class.java) -> {
                CitiesViewModel(HealthRepository(remoteDataSource)) as T
            }
            modelClass.isAssignableFrom(HospitalCovidViewModel::class.java) -> {
                HospitalCovidViewModel(HealthRepository(remoteDataSource)) as T
            }
            modelClass.isAssignableFrom(HospitalNonCovidViewModel::class.java) -> {
                HospitalNonCovidViewModel(HealthRepository(remoteDataSource)) as T
            }
            modelClass.isAssignableFrom(DetailCovidHospitalViewModel::class.java) -> {
                DetailCovidHospitalViewModel(HealthRepository(remoteDataSource)) as T
            }
            modelClass.isAssignableFrom(DetailNonCovidHospitalViewModel::class.java) -> {
                DetailNonCovidHospitalViewModel(HealthRepository(remoteDataSource)) as T
            }
            modelClass.isAssignableFrom(LocationMapHospitalViewModel::class.java) -> {
                LocationMapHospitalViewModel(HealthRepository(remoteDataSource)) as T
            }
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(HealthRepository(remoteDataSource)) as T
            }
            else -> throw Throwable("Unknown ViewModel Class: " + modelClass.name)
        }
}