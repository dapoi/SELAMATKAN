package com.dafdev.selamatkan.di

import android.content.Context
import com.dafdev.selamatkan.data.source.HealthRepository
import com.dafdev.selamatkan.data.source.local.LocalDataSource
import com.dafdev.selamatkan.data.source.local.room.HealthDatabase
import com.dafdev.selamatkan.data.source.remote.RemoteDataSource
import com.dafdev.selamatkan.data.source.remote.network.ApiConfig
import com.dafdev.selamatkan.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): HealthRepository {
        val db = HealthDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(db.healthDao())
        val appExecutors = AppExecutors()

        return HealthRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}