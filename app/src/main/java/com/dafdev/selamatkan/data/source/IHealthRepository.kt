package com.dafdev.selamatkan.data.source

import com.dafdev.selamatkan.data.source.local.model.CitiesEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.data.source.remote.network.ApiResponse
import com.dafdev.selamatkan.data.source.remote.response.*
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IHealthRepository {

    fun getListProvince(): Flow<Resource<List<ProvinceEntity>>>

    fun getListCities(provinceId: String): Flow<Resource<List<CitiesEntity>>>

    fun getListCovidHospital(
        provinceId: String,
        cityId: String
    ): Flow<ApiResponse<List<HospitalsCovidItem>>>

    fun getListNonCovidHospital(
        provinceId: String,
        cityId: String
    ): Flow<ApiResponse<List<HospitalsNonCovidItem>>>

    fun getDetailCovidHospital(hospitalId: String): Flow<ApiResponse<DataHospitalDetail>>

    fun getDetailNonCovidHospital(hospitalId: String): Flow<ApiResponse<DataHospitalDetail>>

    fun getLocationHospital(hospitalId: String): Flow<ApiResponse<DataMapHospital>>
}