package com.dafdev.selamatkan.data.repository

import com.dafdev.selamatkan.data.source.response.*

interface IHealthRepository {

    suspend fun getListProvince(): List<ProvincesItem?>?

    suspend fun getListCities(provinceId: String): List<CitiesItem?>?

    suspend fun getListCovidHospital(
        provinceId: String,
        cityId: String,
    ): List<HospitalsCovidItem?>?

    suspend fun getListNonCovidHospital(
        provinceId: String,
        cityId: String
    ): List<HospitalsNonCovidItem?>?

    suspend fun getDetailCovidHospital(hospitalId: String): List<BedDetailItem?>?

    suspend fun getDetailNonCovidHospital(hospitalId: String): List<BedDetailItem?>?

    suspend fun getLocationHospitalMap(hospitalId: String): DataMapHospital?

}