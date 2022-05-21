package com.dafdev.selamatkan.data.source.local

import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.data.source.local.room.HealthDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val healthDao: HealthDao) {

    suspend fun insertListProvinceHome(provinceEntity: List<ProvinceEntity>) =
        healthDao.insertProvinceHome(provinceEntity)

    fun getListProvinceHome(): Flow<List<ProvinceEntity>> = healthDao.getListProvinceHome()
}