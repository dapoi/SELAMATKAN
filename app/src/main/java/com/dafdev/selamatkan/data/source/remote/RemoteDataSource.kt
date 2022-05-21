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
    private val apiNews: ApiNews,
) {
    fun getDataCovidProv() = flow {
        try {
            val response = apiCovid.getDataCovid().regions
            if (response != null) {
                if (response.isNotEmpty()) {
                    emit(StatusResponseOnline.Success(response))
                    Timber.d(response.toString())
                } else {
                    emit(StatusResponseOnline.Error("Data kosong"))
                    Timber.e("Data kosong")
                }
            }

        } catch (e: Exception) {
            emit(StatusResponseOnline.Error(e.toString()))
            Timber.e(e)
        }
    }.flowOn(Dispatchers.IO)

    fun getListProvinceHome() = flow {
        try {
            val data = apiHospital.getListProvinces().provinces
            if (data.isNotEmpty()) {
                emit(StatusResponse.Success(data))
                Timber.d(data.toString())
            } else {
                emit(StatusResponse.Empty)
            }
        } catch (e: Exception) {
            emit(StatusResponse.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getListCities(provinceId: String) = flow {
        try {
            val data = apiHospital.getListCities(provinceId).cities
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(StatusResponseOnline.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(StatusResponseOnline.Error(data.toString()))
                }
            }
        } catch (e: Exception) {
            emit(StatusResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getListCovidHospital(provinceId: String, cityId: String) = flow {
        try {
            val data = apiHospital.getListHospitalsCovid(provinceId, cityId, "1").hospitals
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(StatusResponseOnline.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(StatusResponseOnline.Error(data.toString()))
                }
            }
        } catch (e: Exception) {
            emit(StatusResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getListNonCovidHospital(provinceId: String, cityId: String) = flow {
        try {
            val data = apiHospital.getListHospitalsNonCovid(provinceId, cityId, "2").hospitals
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(StatusResponseOnline.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(StatusResponseOnline.Error(data.toString()))
                }
            }
        } catch (e: Exception) {
            emit(StatusResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getDetailCovidHospital(hospitalId: String) = flow {
        try {
            val data = apiHospital.getListDetails(hospitalId, "1").data?.bedDetail
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(StatusResponseOnline.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(StatusResponseOnline.Error(data.toString()))
                }
            }
        } catch (e: Exception) {
            emit(StatusResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getDetailNonCovidHospital(hospitalId: String) = flow {
        try {
            val data = apiHospital.getListDetails(hospitalId, "2").data?.bedDetail
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(StatusResponseOnline.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(StatusResponseOnline.Error(data.toString()))
                }
            }
        } catch (e: Exception) {
            emit(StatusResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getLocationHospital(hospitalId: String) = flow {
        try {
            val data = apiHospital.getMapLocation(hospitalId).data
            emit(StatusResponseOnline.Success(data))
            Timber.d(data.toString())
        } catch (e: Exception) {
            emit(StatusResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getNews() = flow {
        try {
            val data = apiNews.getNews().articles
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(StatusResponseOnline.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(StatusResponseOnline.Error("Data kosong"))
                    Timber.e("Data kosong")
                }
            }
        } catch (e: Exception) {
            emit(StatusResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun getNewsSearch(query: String) = flow {
        try {
            val data = apiNews.getNewsSearch(query).articles
            if (data != null) {
                if (data.isNotEmpty()) {
                    emit(StatusResponseOnline.Success(data))
                    Timber.d(data.toString())
                } else {
                    emit(StatusResponseOnline.Error("Data kosong"))
                    Timber.e("Data kosong")
                }
            }
        } catch (e: Exception) {
            emit(StatusResponseOnline.Error(e.message.toString()))
            Timber.e("Remote Data Source, ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}