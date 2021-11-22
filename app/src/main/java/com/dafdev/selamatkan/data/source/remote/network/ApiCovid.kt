package com.dafdev.selamatkan.data.source.remote.network

import com.dafdev.selamatkan.data.source.remote.response.IndoDataCovidResponse
import com.dafdev.selamatkan.data.source.remote.response.ProvinceCovidResponse
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