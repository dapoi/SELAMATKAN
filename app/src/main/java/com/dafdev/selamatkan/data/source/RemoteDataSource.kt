package com.dafdev.selamatkan.data.source

import com.dafdev.selamatkan.data.source.network.ApiService

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getDataCovidIndonesia() = apiService.getDataCovidIndo()

    suspend fun getDataCovidProv() = apiService.getDataCovidProv()

    suspend fun getListProvince() = apiService.getListProvinces().provinces

    suspend fun getListCites(provinceId: String) = apiService.getListCities(provinceId).cities

    suspend fun getListCovidHospital(provinceId: String, cityId: String) =
        apiService.getListHospitalsCovid(provinceId, cityId, "1").hospitals

    suspend fun getListNonCovidHospital(provinceId: String, cityId: String) =
        apiService.getListHospitalsNonCovid(provinceId, cityId, "2").hospitals

    suspend fun getDetailCovidHospital(hospitalId: String) =
        apiService.getListDetails(hospitalId, "1").data?.bedDetail

    suspend fun getDetailNonCovidHospital(hospitalId: String) =
        apiService.getListDetails(hospitalId, "2").data?.bedDetail

    suspend fun getLocationHospitalMap(hospitalId: String) =
        apiService.getMapLocation(hospitalId).data

    suspend fun getNews() = apiService.getNews().articles
}