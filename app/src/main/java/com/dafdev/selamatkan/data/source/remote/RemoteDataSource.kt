package com.dafdev.selamatkan.data.source.remote

import com.dafdev.selamatkan.data.source.remote.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiCovid: ApiCovid,
    private val apiHospital: ApiHospital,
    private val apiNews: ApiNews
) {

    fun getDataCovidIndo() = flow {
        try {
            val data = apiCovid.getDataCovidIndo()
            emit(ApiResponse.Success(data))
            Timber.d(data.toString())
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getDataCovidProv() = flow {
        try {
            val data = apiCovid.getDataCovidProv()
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
            val data = apiHospital.getListProvinces().provinces
            if (data.isNotEmpty()) {
                emit(ApiResponse.Success(data))
                Timber.d(data.toString())
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getListProvinceInside() = flow {
        try {
            val data = apiHospital.getListProvinces().provinces
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

    fun getListCities(provinceId: String) = flow {
        try {
            val data = apiHospital.getListCities(provinceId).cities
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
            val data = apiHospital.getListHospitalsCovid(provinceId, cityId, "1").hospitals
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
            val data = apiHospital.getListHospitalsNonCovid(provinceId, cityId, "2").hospitals
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
            val data = apiHospital.getListDetails(hospitalId, "1").data?.bedDetail
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
            val data = apiHospital.getListDetails(hospitalId, "2").data?.bedDetail
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
            val data = apiHospital.getMapLocation(hospitalId).data
            emit(ApiResponseOnline.Success(data))
            Timber.d(data.toString())
        } catch (e: Exception) {
            emit(ApiResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getNews() = flow {
        try {
            val data = apiNews.getNews().articles
            emit(ApiResponseOnline.Success(data))
            Timber.d(data.toString())
        } catch (e: Exception) {
            emit(ApiResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}