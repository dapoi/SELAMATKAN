package com.dafdev.selamatkan.data.source.remote.network

import com.dafdev.selamatkan.data.source.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * API Data Covid
     */
    @GET("indonesia")
    suspend fun getDataCovidIndo(): IndoDataCovidResponse

    @GET("indonesia/provinsi")
    suspend fun getDataCovidProv(): List<ProvinceCovidResponse>

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

    /**
     * Api Berita
     */
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") countryCode: String = "id",
        @Query("category") category: String = "health",
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    companion object {
        private const val API_KEY = "b5e957cc160649c09671f3dc74c2f3b2"
    }
}