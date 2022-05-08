package com.dafdev.selamatkan.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dafdev.selamatkan.data.source.local.model.NewsEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity

@Database(
    entities = [ProvinceEntity::class, NewsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HealthDatabase : RoomDatabase() {

    abstract fun healthDao(): HealthDao
}