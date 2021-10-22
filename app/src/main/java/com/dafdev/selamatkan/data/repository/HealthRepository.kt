package com.dafdev.selamatkan.data.repository

import com.dafdev.selamatkan.data.source.RemoteDataSource
import com.dafdev.selamatkan.data.source.response.*

class HealthRepository(
    private val remoteDataSource: RemoteDataSource,
) : IHealthRepository {

    override suspend fun getListProvince(): List<ProvincesItem?>? =
        remoteDataSource.getListProvince()

    override suspend fun getListCities(provinceId: String): List<CitiesItem?>? =
        remoteDataSource.getListCites(provinceId)

    override suspend fun getListCovidHospital(
        provinceId: String,
        cityId: String
    ): List<HospitalsCovidItem?>? =
        remoteDataSource.getListCovidHospital(provinceId, cityId)

    override suspend fun getListNonCovidHospital(
        provinceId: String,
        cityId: String
    ): List<HospitalsNonCovidItem> =
        remoteDataSource.getListNonCovidHospital(provinceId, cityId) as List<HospitalsNonCovidItem>

    override suspend fun getDetailCovidHospital(hospitalId: String): List<BedDetailItem> =
        remoteDataSource.getDetailCovidHospital(hospitalId) as List<BedDetailItem>

    override suspend fun getDetailNonCovidHospital(hospitalId: String): List<BedDetailItem> =
        remoteDataSource.getDetailNonCovidHospital(hospitalId) as List<BedDetailItem>

    override suspend fun getLocationHospitalMap(hospitalId: String): DataMapHospital =
        remoteDataSource.getLocationHospitalMap(hospitalId)!!
}