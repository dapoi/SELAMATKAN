package com.dafdev.selamatkan.data.source.remote.network

import com.dafdev.selamatkan.data.source.remote.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiHospital {

    /**
     * Api Rumah Sakit
     */
    @GET("get-provinces")
    suspend fun getListProvinces(): ProvinceResponse

    @GET("get-cities?")
    suspend fun getListCities(
        @Query("provinceid") provinceId: String
    ): CitiesResponse

    @GET("get-hospitals?")
    suspend fun getListHospitalsCovid(
        @Query("provinceid") provinceId: String,
        @Query("cityid") cityId: String,
        @Query("type") type: String
    ): HospitalCovidResponse

    @GET("get-hospitals?")
    suspend fun getListHospitalsNonCovid(
        @Query("provinceid") provinceId: String,
        @Query("cityid") cityId: String,
        @Query("type") type: String
    ): HospitalNonCovidResponse

    @GET("get-bed-detail?")
    suspend fun getListDetails(
        @Query("hospitalid") hospitalId: String,
        @Query("type") type: String
    ): DetailResponse

    @GET("get-hospital-map?")
    suspend fun getMapLocation(
        @Query("hospitalid") hospitalId: String
    ): HospitalLocationResponse
}