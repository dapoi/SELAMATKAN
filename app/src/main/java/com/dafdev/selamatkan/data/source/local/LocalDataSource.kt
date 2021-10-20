package com.dafdev.selamatkan.data.source.local

import com.dafdev.selamatkan.data.source.local.model.CitiesEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.data.source.local.room.HealthDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource private constructor(private val healthDao: HealthDao) {

    fun getListProvince(): Flow<List<ProvinceEntity>> = healthDao.getListProvince()

    fun getListCities(): Flow<List<CitiesEntity>> = healthDao.getListCity()

    suspend fun insertListProvince(provinceEntity: List<ProvinceEntity>) =
        healthDao.insertListProvince(provinceEntity)

    suspend fun insertListCities(citiesEntity: List<CitiesEntity>) =
        healthDao.insertListCity(citiesEntity)

    companion object {

        @Volatile
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(healthDao: HealthDao): LocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalDataSource(healthDao)
            }
    }
}