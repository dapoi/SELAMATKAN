package com.dafdev.selamatkan.data.source.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    fun provideApiHospital(): ApiService {
        val moshi = MoshiConverterFactory.create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rs-bed-covid-api.vercel.app/api/")
            .addConverterFactory(moshi)
            .client(provideOkHttpClient())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun provideApiCovid(): ApiService {
        val moshi = MoshiConverterFactory.create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://apicovid19indonesia-v2.vercel.app/api/")
            .addConverterFactory(moshi)
            .client(provideOkHttpClient())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}