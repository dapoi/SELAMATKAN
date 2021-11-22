package com.dafdev.selamatkan.data.repository

import com.dafdev.selamatkan.data.domain.model.*
import com.dafdev.selamatkan.data.source.NetworkBoundResource
import com.dafdev.selamatkan.data.source.NetworkOnlyResource
import com.dafdev.selamatkan.data.source.local.LocalDataSource
import com.dafdev.selamatkan.data.source.local.model.CovidIndoEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.data.source.remote.RemoteDataSource
import com.dafdev.selamatkan.data.source.remote.network.ApiResponse
import com.dafdev.selamatkan.data.source.remote.network.ApiResponseOnline
import com.dafdev.selamatkan.data.source.remote.response.*
import com.dafdev.selamatkan.utils.AppExecutors
import com.dafdev.selamatkan.utils.DataMapper
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IHealthRepository {

    override fun getDataCovidIndonesia(): Flow<Resource<CovidIndoEntity>> {
        return object : NetworkBoundResource<CovidIndoEntity, IndoDataCovidResponse>(appExecutors) {
            override fun loadFromDB(): Flow<CovidIndoEntity> =
                localDataSource.getCovidIndo()

            override fun shouldFetch(data: CovidIndoEntity?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<IndoDataCovidResponse>> =
                remoteDataSource.getDataCovidIndo()

            override suspend fun saveCallResult(data: IndoDataCovidResponse) {
                val entity = DataMapper.mapCovidResponseToEntity(data)
                localDataSource.insertCovidIndo(entity)
            }
        }.asFlow()
    }

    override fun getDataCovidProv(): Flow<Resource<List<CovidProv>>> {
        return object : NetworkOnlyResource<List<CovidProv>, List<ProvinceCovidResponse>>() {
            override fun loadFromNetwork(data: List<ProvinceCovidResponse>): Flow<List<CovidProv>> =
                DataMapper.mapProvinceCovidResponseToProvince(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<ProvinceCovidResponse>>> =
                remoteDataSource.getDataCovidProv()
        }.asFlow()
    }

    override fun getListProvinceHome(): Flow<Resource<List<ProvinceEntity>>> {
        return object :
            NetworkBoundResource<List<ProvinceEntity>, List<ProvincesItem>>(appExecutors) {
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

    override fun getListProvinceInside(): Flow<Resource<List<Province>>> {
        return object : NetworkOnlyResource<List<Province>, List<ProvincesItem>>() {
            override fun loadFromNetwork(data: List<ProvincesItem>): Flow<List<Province>> =
                DataMapper.mapProvinceResponseToProvince(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<ProvincesItem>>> =
                remoteDataSource.getListProvinceInside()
        }.asFlow()
    }

    override fun getListCities(provinceId: String): Flow<Resource<List<Cities>>> {
        return object : NetworkOnlyResource<List<Cities>, List<CitiesItem>>() {
            override fun loadFromNetwork(data: List<CitiesItem>): Flow<List<Cities>> =
                DataMapper.mapCitiesResponseToCities(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<CitiesItem>>> =
                remoteDataSource.getListCities(provinceId) as Flow<ApiResponseOnline<List<CitiesItem>>>
        }.asFlow()
    }

    override fun getListCovidHospital(
        provinceId: String,
        cityId: String
    ): Flow<Resource<List<HospitalCovid>>> {
        return object : NetworkOnlyResource<List<HospitalCovid>, List<HospitalsCovidItem>>() {
            override fun loadFromNetwork(data: List<HospitalsCovidItem>): Flow<List<HospitalCovid>> =
                DataMapper.mapHospitalCovidResponseToHospitalCovid(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<HospitalsCovidItem>>> =
                remoteDataSource.getListCovidHospital(
                    provinceId,
                    cityId
                ) as Flow<ApiResponseOnline<List<HospitalsCovidItem>>>
        }.asFlow()
    }

    override fun getListNonCovidHospital(
        provinceId: String,
        cityId: String
    ): Flow<Resource<List<HospitalNonCovid>>> {
        return object : NetworkOnlyResource<List<HospitalNonCovid>, List<HospitalsNonCovidItem>>() {
            override fun loadFromNetwork(data: List<HospitalsNonCovidItem>): Flow<List<HospitalNonCovid>> =
                DataMapper.mapHospitalNonCovidResponseToHospitalNonCovid(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<HospitalsNonCovidItem>>> =
                remoteDataSource.getListNonCovidHospital(
                    provinceId,
                    cityId
                ) as Flow<ApiResponseOnline<List<HospitalsNonCovidItem>>>
        }.asFlow()
    }

    override fun getDetailCovidHospital(hospitalId: String): Flow<Resource<List<DetailHospital>>> {
        return object : NetworkOnlyResource<List<DetailHospital>, List<BedDetailItem>>() {
            override fun loadFromNetwork(data: List<BedDetailItem>): Flow<List<DetailHospital>> =
                DataMapper.mapHospitalDetailResponseToHospitalDetail(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<BedDetailItem>>> =
                remoteDataSource.getDetailCovidHospital(hospitalId) as Flow<ApiResponseOnline<List<BedDetailItem>>>
        }.asFlow()
    }

    override fun getDetailNonCovidHospital(hospitalId: String): Flow<Resource<List<DetailHospital>>> {
        return object : NetworkOnlyResource<List<DetailHospital>, List<BedDetailItem>>() {
            override fun loadFromNetwork(data: List<BedDetailItem>): Flow<List<DetailHospital>> =
                DataMapper.mapHospitalDetailResponseToHospitalDetail(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<List<BedDetailItem>>> =
                remoteDataSource.getDetailNonCovidHospital(hospitalId) as Flow<ApiResponseOnline<List<BedDetailItem>>>
        }.asFlow()
    }

    override fun getLocationHospitalMap(hospitalId: String): Flow<Resource<Location>> {
        return object : NetworkOnlyResource<Location, DataMapHospital>() {
            override fun loadFromNetwork(data: DataMapHospital): Flow<Location> =
                DataMapper.mapLocationResponseToLocation(data)

            override suspend fun createCall(): Flow<ApiResponseOnline<DataMapHospital>> =
                remoteDataSource.getLocationHospital(hospitalId) as Flow<ApiResponseOnline<DataMapHospital>>
        }.asFlow()
    }

    override fun getNews(): Flow<Resource<List<News>>> {
        return object : NetworkBoundResource<List<News>, List<Articles>>(appExecutors) {
            override fun loadFromDB(): Flow<List<News>> =
                localDataSource.getListNews().map {
                    DataMapper.mapNewsEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<News>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<Articles>>> =
                remoteDataSource.getNews()

            override suspend fun saveCallResult(data: List<Articles>) {
                val entity = DataMapper.mapNewsResponseToEntity(data)
                localDataSource.insertListNews(entity)
            }
        }.asFlow()
    }
}