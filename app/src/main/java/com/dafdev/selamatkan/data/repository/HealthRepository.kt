package com.dafdev.selamatkan.data.repository

import com.dafdev.selamatkan.data.domain.model.*
import com.dafdev.selamatkan.data.source.NetworkBoundResource
import com.dafdev.selamatkan.data.source.NetworkOnlyResource
import com.dafdev.selamatkan.data.source.local.LocalDataSource
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.data.source.remote.RemoteDataSource
import com.dafdev.selamatkan.data.source.remote.model.*
import com.dafdev.selamatkan.data.source.remote.network.ApiResponse
import com.dafdev.selamatkan.data.source.remote.network.ApiResponseOnline
import com.dafdev.selamatkan.utils.DataMapper
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IHealthRepository {

    override fun getDataCovidProv(): Flow<Resource<List<CovidProv>>> {
        return object : NetworkOnlyResource<List<CovidProv>, List<RegionsItem?>>() {
            override fun loadFromNetwork(data: List<RegionsItem?>): Flow<List<CovidProv>> =
                DataMapper.mapDataCovidResponseToCovidProvince(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<RegionsItem?>>> =
                remoteDataSource.getDataCovidProv()
        }.asFlow()
    }

    override fun getListProvinceHome(): Flow<Resource<List<ProvinceEntity>>> {
        return object :
            NetworkBoundResource<List<ProvinceEntity>, List<ProvincesItem>>() {
            override fun loadFromDB(): Flow<List<ProvinceEntity>> =
                localDataSource.getListProvinceHome()

            override fun shouldFetch(data: List<ProvinceEntity>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ProvincesItem>>> =
                remoteDataSource.getListProvinceHome()

            override suspend fun saveCallResult(data: List<ProvincesItem>) {
                val entity = DataMapper.mapProvinceResponseToEntity(data)
                localDataSource.insertListProvinceHome(entity)
            }
        }.asFlow()
    }

    override fun getListCities(provinceId: String): Flow<Resource<List<Cities>>> {
        return object : NetworkOnlyResource<List<Cities>, List<CitiesItem?>>() {
            override fun loadFromNetwork(data: List<CitiesItem?>): Flow<List<Cities>> =
                DataMapper.mapCitiesResponseToCities(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<CitiesItem?>>> =
                remoteDataSource.getListCities(provinceId)
        }.asFlow()
    }

    override fun getListCovidHospital(
        provinceId: String,
        cityId: String
    ): Flow<Resource<List<HospitalCovid>>> {
        return object : NetworkOnlyResource<List<HospitalCovid>, List<HospitalsCovidItem?>>() {
            override fun loadFromNetwork(data: List<HospitalsCovidItem?>): Flow<List<HospitalCovid>> =
                DataMapper.mapHospitalCovidResponseToHospitalCovid(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<HospitalsCovidItem?>>> =
                remoteDataSource.getListCovidHospital(
                    provinceId,
                    cityId
                )
        }.asFlow()
    }

    override fun getListNonCovidHospital(
        provinceId: String,
        cityId: String
    ): Flow<Resource<List<HospitalNonCovid>>> {
        return object :
            NetworkOnlyResource<List<HospitalNonCovid>, List<HospitalsNonCovidItem?>>() {
            override fun loadFromNetwork(data: List<HospitalsNonCovidItem?>): Flow<List<HospitalNonCovid>> =
                DataMapper.mapHospitalNonCovidResponseToHospitalNonCovid(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<HospitalsNonCovidItem?>>> =
                remoteDataSource.getListNonCovidHospital(
                    provinceId,
                    cityId
                )
        }.asFlow()
    }

    override fun getDetailCovidHospital(hospitalId: String): Flow<Resource<List<DetailHospital>>> {
        return object : NetworkOnlyResource<List<DetailHospital>, List<BedDetailItem?>>() {
            override fun loadFromNetwork(data: List<BedDetailItem?>): Flow<List<DetailHospital>> =
                DataMapper.mapHospitalDetailResponseToHospitalDetail(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<BedDetailItem?>>> =
                remoteDataSource.getDetailCovidHospital(hospitalId)
        }.asFlow()
    }

    override fun getDetailNonCovidHospital(hospitalId: String): Flow<Resource<List<DetailHospital>>> {
        return object : NetworkOnlyResource<List<DetailHospital>, List<BedDetailItem?>>() {
            override fun loadFromNetwork(data: List<BedDetailItem?>): Flow<List<DetailHospital>> =
                DataMapper.mapHospitalDetailResponseToHospitalDetail(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<BedDetailItem?>>> =
                remoteDataSource.getDetailNonCovidHospital(hospitalId)
        }.asFlow()
    }

    override fun getLocationHospitalMap(hospitalId: String): Flow<Resource<Location>> {
        return object : NetworkOnlyResource<Location, DataMapHospital?>() {
            override fun loadFromNetwork(data: DataMapHospital?): Flow<Location> =
                DataMapper.mapLocationResponseToLocation(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<DataMapHospital?>> =
                remoteDataSource.getLocationHospital(hospitalId)
        }.asFlow()
    }

    override fun getNews(): Flow<Resource<List<News>>> {
        return object : NetworkOnlyResource<List<News>, List<Articles?>?>() {
            override fun loadFromNetwork(data: List<Articles?>?): Flow<List<News>> =
                DataMapper.mapArticlesToNews(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<Articles?>?>> =
                remoteDataSource.getNews()

        }.asFlow()
    }
}