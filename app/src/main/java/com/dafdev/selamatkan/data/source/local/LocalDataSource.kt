package com.dafdev.selamatkan.data.source.local

import com.dafdev.selamatkan.data.source.local.model.CovidIndoEntity
import com.dafdev.selamatkan.data.source.local.model.NewsEntity
import com.dafdev.selamatkan.data.source.local.model.NewsFavEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.data.source.local.room.HealthDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource private constructor(private val healthDao: HealthDao) {

    fun getCovidIndo(): Flow<CovidIndoEntity> = healthDao.getDataCovidIndo()

    fun getListProvinceHome(): Flow<List<ProvinceEntity>> = healthDao.getListProvinceHome()

    fun getListNews(): Flow<List<NewsEntity>> = healthDao.getListNews()

    suspend fun insertCovidIndo(covidEntity: CovidIndoEntity) =
        healthDao.insertCovidIndo(covidEntity)

    suspend fun insertListProvinceHome(provinceEntity: List<ProvinceEntity>) =
        healthDao.insertProvinceHome(provinceEntity)

    suspend fun insertListNews(newsEntity: List<NewsEntity>) = healthDao.insertNews(newsEntity)

    fun updateFavNews(newsFavEntity: NewsFavEntity, fav: Boolean) {
        newsFavEntity.isFav = fav
        healthDao.updateFavNews(newsFavEntity)
    }

    fun getFavNews(): Flow<List<NewsFavEntity>> = healthDao.getFavNews()

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(newsDao: HealthDao): LocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalDataSource(newsDao)
            }
    }
}