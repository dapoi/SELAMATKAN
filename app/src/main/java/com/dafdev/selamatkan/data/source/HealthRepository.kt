package com.dafdev.selamatkan.data.source

import com.dafdev.selamatkan.data.source.local.LocalDataSource
import com.dafdev.selamatkan.data.source.local.model.CitiesEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.data.source.remote.RemoteDataSource
import com.dafdev.selamatkan.data.source.remote.network.ApiResponse
import com.dafdev.selamatkan.data.source.remote.response.*
import com.dafdev.selamatkan.utils.AppExecutors
import com.dafdev.selamatkan.utils.DataMapper
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.flow.Flow

class HealthRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IHealthRepository {
    override fun getListProvince(): Flow<Resource<List<ProvinceEntity>>> =
        object : NetworkBoundResource<List<ProvinceEntity>, List<ProvincesItem>>(appExecutors) {
            override fun loadFromDB(): Flow<List<ProvinceEntity>> =
                localDataSource.getListProvince()

            override fun shouldFetch(data: List<ProvinceEntity>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ProvincesItem>>> =
                remoteDataSource.getListProvince() as Flow<ApiResponse<List<ProvincesItem>>>

            override suspend fun saveCallResult(data: List<ProvincesItem>) {
                val dataEntity = DataMapper.mapProvinceResponseToEntity(data)
                localDataSource.insertListProvince(dataEntity)
            }
        }.asFlow()

    override fun getListCities(provinceId: String): Flow<Resource<List<CitiesEntity>>> =
        object : NetworkBoundResource<List<CitiesEntity>, List<CitiesItem>>(appExecutors) {
            override fun loadFromDB(): Flow<List<CitiesEntity>> =
                localDataSource.getListCities()

            override fun shouldFetch(data: List<CitiesEntity>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<CitiesItem>>> =
                remoteDataSource.getListCities(provinceId) as Flow<ApiResponse<List<CitiesItem>>>

            override suspend fun saveCallResult(data: List<CitiesItem>) =
                DataMapper.mapCitiesResponseToEntity(data).let {
                    localDataSource.insertListCities(it)
                }
        }.asFlow()

    override fun getListCovidHospital(
        provinceId: String,
        cityId: String
    ): Flow<ApiResponse<List<HospitalsCovidItem>>> =
        remoteDataSource.getListCovidHospital(
            provinceId,
            cityId
        ) as Flow<ApiResponse<List<HospitalsCovidItem>>>

    override fun getListNonCovidHospital(
        provinceId: String,
        cityId: String
    ): Flow<ApiResponse<List<HospitalsNonCovidItem>>> =
        remoteDataSource.getListNonCovidHospital(
            provinceId,
            cityId
        ) as Flow<ApiResponse<List<HospitalsNonCovidItem>>>

    override fun getDetailCovidHospital(hospitalId: String): Flow<ApiResponse<DataHospitalDetail>> =
        remoteDataSource.getDetailCovidHospital(hospitalId) as Flow<ApiResponse<DataHospitalDetail>>

    override fun getDetailNonCovidHospital(hospitalId: String): Flow<ApiResponse<DataHospitalDetail>> =
        remoteDataSource.getDetailNonCovidHospital(hospitalId) as Flow<ApiResponse<DataHospitalDetail>>

    override fun getLocationHospital(hospitalId: String): Flow<ApiResponse<DataMapHospital>> =
        remoteDataSource.getLocationHospitalMap(hospitalId) as Flow<ApiResponse<DataMapHospital>>


    companion object {

        @Volatile
        private var INSTANCE: HealthRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): HealthRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HealthRepository(remoteDataSource, localDataSource, appExecutors)
        }
    }
}