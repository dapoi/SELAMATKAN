package com.dafdev.selamatkan.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dafdev.selamatkan.data.source.local.model.CovidIndoEntity
import com.dafdev.selamatkan.data.source.local.model.NewsEntity
import com.dafdev.selamatkan.data.source.local.model.NewsFavEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthDao {

    @Query("SELECT * FROM covid")
    fun getDataCovidIndo(): Flow<CovidIndoEntity>

    @Query("SELECT * FROM province")
    fun getListProvinceHome(): Flow<List<ProvinceEntity>>

    @Query("SELECT * FROM news")
    fun getListNews(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCovidIndo(covidEntity: CovidIndoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProvinceHome(provinceEntity: List<ProvinceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsEntity: List<NewsEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun updateFavNews(newsEntity: NewsFavEntity)

    @Query("SELECT * FROM fav_news WHERE favorite = 1")
    fun getFavNews(): Flow<List<NewsFavEntity>>
}