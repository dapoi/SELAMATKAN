package com.dafdev.selamatkan.data.repository

import com.dafdev.selamatkan.data.source.RemoteDataSource
import com.dafdev.selamatkan.data.source.response.*

class HealthRepository(
    private val remoteDataSource: RemoteDataSource,
) : IHealthRepository {

    override suspend fun getListProvince(): List<ProvincesItem> =
        remoteDataSource.getListProvince() as List<ProvincesItem>

    override suspend fun getListCities(provinceId: String): List<CitiesItem> =
        remoteDataSource.getListCites(provinceId) as List<CitiesItem>

    override suspend fun getListCovidHospital(
        provinceId: String,
        cityId: String
    ): List<HospitalsCovidItem> =
        remoteDataSource.getListCovidHospital(provinceId, cityId) as List<HospitalsCovidItem>

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


    companion object {

        @Volatile
        private var INSTANCE: HealthRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
        ): HealthRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HealthRepository(remoteDataSource)
        }
    }
}