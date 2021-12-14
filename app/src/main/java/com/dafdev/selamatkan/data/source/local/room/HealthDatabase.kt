package com.dafdev.selamatkan.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dafdev.selamatkan.data.source.local.model.CovidIndoEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity

@Database(
    entities = [CovidIndoEntity::class, ProvinceEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HealthDatabase : RoomDatabase() {

    abstract fun healthDao(): HealthDao
}