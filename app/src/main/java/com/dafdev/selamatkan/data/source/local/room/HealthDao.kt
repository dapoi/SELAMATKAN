package com.dafdev.selamatkan.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthDao {

    @Query("SELECT * FROM province")
    fun getListProvinceHome(): Flow<List<ProvinceEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProvinceHome(provinceEntity: List<ProvinceEntity>)
}