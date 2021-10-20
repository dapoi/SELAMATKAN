package com.dafdev.selamatkan.data.source.remote

import com.dafdev.selamatkan.data.source.remote.network.ApiResponse
import com.dafdev.selamatkan.data.source.remote.network.ApiService
import com.dafdev.selamatkan.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class RemoteDataSource private constructor(private val apiService: ApiService) {

    fun getListProvince(): Flow<ApiResponse<List<ProvincesItem?>?>> =
        flow {
            try {
                val response = apiService.getListProvinces().provinces
                if (response!!.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                    Timber.d("onSuccess $response")
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Timber.e("onFailure " + e.message.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getListCities(provinceId: String): Flow<ApiResponse<List<CitiesItem?>?>> =
        flow {
            try {
                val response = apiService.getListCities(provinceId).cities
                if (response!!.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                    Timber.d("onSuccess $response")
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Timber.e("onFailure " + e.message.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getListCovidHospital(
        provinceId: String,
        cityId: String
    ): Flow<ApiResponse<List<HospitalsCovidItem?>?>> =
        flow {
            try {
                val response = apiService.getListHospitalsCovid(provinceId, cityId, "1").hospitals
                if (response!!.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                    Timber.d("onSuccess $response")
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Timber.e("onFailure " + e.message.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getListNonCovidHospital(
        provinceId: String,
        cityId: String
    ): Flow<ApiResponse<List<HospitalsNonCovidItem?>?>> =
        flow {
            try {
                val response =
                    apiService.getListHospitalsNonCovid(provinceId, cityId, "2").hospitals
                if (response!!.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                    Timber.d("onSuccess $response")
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Timber.e("onFailure " + e.message.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getDetailCovidHospital(hospitalId: String): Flow<ApiResponse<DataHospitalDetail?>> =
        flow {
            try {
                val response = apiService.getListDetails(hospitalId, "1").dataHospitalDetail
                emit(ApiResponse.Success(response))
                Timber.d("onSuccess $response")
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Timber.e("onFailure " + e.message.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getDetailNonCovidHospital(hospitalId: String): Flow<ApiResponse<DataHospitalDetail?>> =
        flow {
            try {
                val response = apiService.getListDetails(hospitalId, "2").dataHospitalDetail
                emit(ApiResponse.Success(response))
                Timber.d("onSuccess $response")
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Timber.e("onFailure " + e.message.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getLocationHospitalMap(hospitalId: String): Flow<ApiResponse<DataMapHospital?>> =
        flow {
            try {
                val response = apiService.getMapLocation(hospitalId).dataMap
                emit(ApiResponse.Success(response))
                Timber.d("onSuccess $response")
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Timber.e("onFailure " + e.message.toString())
            }
        }.flowOn(Dispatchers.IO)

    companion object {

        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RemoteDataSource(apiService)
            }
    }
}