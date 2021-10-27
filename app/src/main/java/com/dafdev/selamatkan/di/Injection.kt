package com.dafdev.selamatkan.di

import android.content.Context
import com.dafdev.selamatkan.data.domain.usecase.HealthInteractor
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase
import com.dafdev.selamatkan.data.repository.HealthRepository
import com.dafdev.selamatkan.data.repository.IHealthRepository
import com.dafdev.selamatkan.data.source.local.LocalDataSource
import com.dafdev.selamatkan.data.source.local.room.HealthDatabase
import com.dafdev.selamatkan.data.source.remote.RemoteDataSource
import com.dafdev.selamatkan.data.source.remote.network.ApiConfig
import com.dafdev.selamatkan.utils.AppExecutors

object Injection {

    private fun provideRepository(context: Context): IHealthRepository {
        val db = HealthDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(
            ApiConfig.provideApiHospital(),
            ApiConfig.provideApiCovid(),
            ApiConfig.provideApiNews()
        )
        val localDataSource = LocalDataSource.getInstance(db.healthDao())
        val appExecutors = AppExecutors()

        return HealthRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideHealthUseCase(context: Context): HealthUseCase {
        val repository = provideRepository(context)
        return HealthInteractor(repository)
    }
}