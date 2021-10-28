package com.dafdev.selamatkan.data.repository

import com.dafdev.selamatkan.data.domain.model.*
import com.dafdev.selamatkan.data.source.local.model.CovidIndoEntity
import com.dafdev.selamatkan.data.source.local.model.NewsEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IHealthRepository {

    fun getDataCovidIndonesia(): Flow<Resource<CovidIndoEntity>>

    fun getDataCovidProv(): Flow<Resource<List<CovidProv>>>

    fun getListProvinceHome(): Flow<Resource<List<ProvinceEntity>>>

    fun getListProvinceInside(): Flow<Resource<List<Province>>>

    fun getListCities(provinceId: String): Flow<Resource<List<Cities>>>

    fun getListCovidHospital(
        provinceId: String,
        cityId: String,
    ): Flow<Resource<List<HospitalCovid>>>

    fun getListNonCovidHospital(
        provinceId: String,
        cityId: String
    ): Flow<Resource<List<HospitalNonCovid>>>

    fun getDetailCovidHospital(hospitalId: String): Flow<Resource<List<DetailHospital>>>

    fun getDetailNonCovidHospital(hospitalId: String): Flow<Resource<List<DetailHospital>>>

    fun getLocationHospitalMap(hospitalId: String): Flow<Resource<Location>>

    fun getNews(): Flow<Resource<List<News>>>

    fun updateFavNews(newsEntity: News, fav: Boolean)

    fun getFavNews(): Flow<List<News>>
}