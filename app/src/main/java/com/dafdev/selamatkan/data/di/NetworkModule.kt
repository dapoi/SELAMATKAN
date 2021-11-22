package com.dafdev.selamatkan.data.di

import com.dafdev.selamatkan.data.source.remote.network.ApiCovid
import com.dafdev.selamatkan.data.source.remote.network.ApiHospital
import com.dafdev.selamatkan.data.source.remote.network.ApiNews
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiCovid(@Covid retrofit: Retrofit): ApiCovid =
        retrofit.create(ApiCovid::class.java)

    @Singleton
    @Provides
    fun provideApiHospital(@Hospital retrofit: Retrofit): ApiHospital =
        retrofit.create(ApiHospital::class.java)

    @Singleton
    @Provides
    fun provideApiNews(@News retrofit: Retrofit): ApiNews =
        retrofit.create(ApiNews::class.java)
}