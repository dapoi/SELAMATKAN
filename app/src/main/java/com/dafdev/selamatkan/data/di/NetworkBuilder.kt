package com.dafdev.selamatkan.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkBuilder {
    @Singleton
    @Provides
    fun provideMoshi(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideGson(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    @Covid
    fun provideApiCovid(): Retrofit {
        val gson = GsonConverterFactory.create()
        return Retrofit.Builder()
            .baseUrl("https://apicovid19indonesia-v2.vercel.app/api/")
            .addConverterFactory(gson)
            .client(provideOkHttpClient())
            .build()
    }

    @Singleton
    @Provides
    @Hospital
    fun provideApiHospital(): Retrofit {
        val moshi = MoshiConverterFactory.create()
        return Retrofit.Builder()
            .baseUrl("https://rs-bed-covid-api.vercel.app/api/")
            .addConverterFactory(moshi)
            .client(provideOkHttpClient())
            .build()
    }

    @Singleton
    @Provides
    @News
    fun provideApiNews(): Retrofit {
        val gson = GsonConverterFactory.create()
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(gson)
            .client(provideOkHttpClient())
            .build()
    }
}