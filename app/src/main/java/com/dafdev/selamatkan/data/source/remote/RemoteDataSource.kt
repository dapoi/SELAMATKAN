package com.dafdev.selamatkan.data.source.remote

import com.dafdev.selamatkan.data.source.remote.network.ApiResponse
import com.dafdev.selamatkan.data.source.remote.network.ApiResponseOnline
import com.dafdev.selamatkan.data.source.remote.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class RemoteDataSource private constructor(private val apiService: ApiService) {

    fun getDataCovidIndo() = flow {
        try {
            val data = apiService.getDataCovidIndo()
            emit(ApiResponse.Success(data))
            Timber.d(data.toString())
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getDataCovidProv() = flow {
        try {
            val data = apiService.getDataCovidProv()
            if (data.isNotEmpty()) {
                emit(ApiResponseOnline.Success(data))
                Timber.d(data.toString())
            } else {
                emit(ApiResponseOnline.Error(data.toString()))
            }
        } catch (e: Exception) {
            emit(ApiResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getListProvinceHome() = flow {
        try {
            val data = apiService.getListProvinces().provinces
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(ApiResponse.Empty)
                }
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getListProvinceInside() = flow {
        try {
            val data = apiService.getListProvinces().provinces
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(ApiResponseOnline.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(ApiResponseOnline.Error(data.toString()))
                }
            }
        } catch (e: Exception) {
            emit(ApiResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getListCities(provinceId: String) = flow {
        try {
            val data = apiService.getListCities(provinceId).cities
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(ApiResponseOnline.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(ApiResponseOnline.Error(data.toString()))
                }
            }
        } catch (e: Exception) {
            emit(ApiResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getListCovidHospital(provinceId: String, cityId: String) = flow {
        try {
            val data = apiService.getListHospitalsCovid(provinceId, cityId, "1").hospitals
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(ApiResponseOnline.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(ApiResponseOnline.Error(data.toString()))
                }
            }
        } catch (e: Exception) {
            emit(ApiResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getListNonCovidHospital(provinceId: String, cityId: String) = flow {
        try {
            val data = apiService.getListHospitalsNonCovid(provinceId, cityId, "2").hospitals
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(ApiResponseOnline.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(ApiResponseOnline.Error(data.toString()))
                }
            }
        } catch (e: Exception) {
            emit(ApiResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getDetailCovidHospital(hospitalId: String) = flow {
        try {
            val data = apiService.getListDetails(hospitalId, "1").data?.bedDetail
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(ApiResponseOnline.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(ApiResponseOnline.Error(data.toString()))
                }
            }
        } catch (e: Exception) {
            emit(ApiResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getDetailNonCovidHospital(hospitalId: String) = flow {
        try {
            val data = apiService.getListDetails(hospitalId, "2").data?.bedDetail
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(ApiResponseOnline.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(ApiResponseOnline.Error(data.toString()))
                }
            }
        } catch (e: Exception) {
            emit(ApiResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getLocationHospital(hospitalId: String) = flow {
        try {
            val data = apiService.getMapLocation(hospitalId).data
            emit(ApiResponseOnline.Success(data))
            Timber.d(data.toString())
        } catch (e: Exception) {
            emit(ApiResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getNews() = flow {
        try {
            val data = apiService.getNews().articles
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(ApiResponse.Empty)
                }
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
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