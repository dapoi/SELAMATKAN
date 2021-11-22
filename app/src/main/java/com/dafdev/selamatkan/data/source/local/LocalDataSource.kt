package com.dafdev.selamatkan.data.source.local

import com.dafdev.selamatkan.data.source.local.model.CovidIndoEntity
import com.dafdev.selamatkan.data.source.local.model.NewsEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.data.source.local.room.HealthDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val healthDao: HealthDao) {

    fun getCovidIndo(): Flow<CovidIndoEntity> = healthDao.getDataCovidIndo()

    fun getListProvinceHome(): Flow<List<ProvinceEntity>> = healthDao.getListProvinceHome()

    fun getListNews(): Flow<List<NewsEntity>> = healthDao.getListNews()

    suspend fun insertCovidIndo(covidEntity: CovidIndoEntity) =
        healthDao.insertCovidIndo(covidEntity)

    suspend fun insertListProvinceHome(provinceEntity: List<ProvinceEntity>) =
        healthDao.insertProvinceHome(provinceEntity)

    suspend fun insertListNews(newsEntity: List<NewsEntity>) = healthDao.insertNews(newsEntity)
}