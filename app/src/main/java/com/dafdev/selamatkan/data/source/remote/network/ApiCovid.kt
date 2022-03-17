package com.dafdev.selamatkan.data.source.remote.network

import com.dafdev.selamatkan.data.source.remote.model.IndoDataCovidResponse
import com.dafdev.selamatkan.data.source.remote.model.ProvinceCovidResponse
import retrofit2.http.GET

interface ApiCovid {
    /**
     * API Data Covid
     */
    @GET("indonesia")
    suspend fun getDataCovidIndo(): IndoDataCovidResponse

    @GET("indonesia/provinsi")
    suspend fun getDataCovidProv(): List<ProvinceCovidResponse>
}