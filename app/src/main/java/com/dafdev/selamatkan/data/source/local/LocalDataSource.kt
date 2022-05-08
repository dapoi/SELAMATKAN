package com.dafdev.selamatkan.data.source.local

import com.dafdev.selamatkan.data.source.local.model.NewsEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.data.source.local.room.HealthDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val healthDao: HealthDao) {

    suspend fun insertListProvinceHome(provinceEntity: List<ProvinceEntity>) =
        healthDao.insertProvinceHome(provinceEntity)

    suspend fun insertNews(newsEntity: List<NewsEntity>) =
        healthDao.insertNews(newsEntity)

    fun getListProvinceHome(): Flow<List<ProvinceEntity>> = healthDao.getListProvinceHome()

    fun getListNews(): Flow<List<NewsEntity>> = healthDao.getListNews()
}