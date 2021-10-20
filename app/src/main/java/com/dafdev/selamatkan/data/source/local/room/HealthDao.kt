package com.dafdev.selamatkan.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dafdev.selamatkan.data.source.local.model.CitiesEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthDao {

    @Query("SELECT * FROM province")
    fun getListProvince(): Flow<List<ProvinceEntity>>

    @Query("SELECT * FROM city")
    fun getListCity(): Flow<List<CitiesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListProvince(provinceEntity: List<ProvinceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListCity(citiesEntity: List<CitiesEntity>)
}