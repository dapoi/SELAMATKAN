package com.dafdev.selamatkan.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dafdev.selamatkan.data.source.local.model.CovidIndoEntity
import com.dafdev.selamatkan.data.source.local.model.NewsEntity
import com.dafdev.selamatkan.data.source.local.model.NewsFavEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity

@Database(
    entities = [CovidIndoEntity::class, ProvinceEntity::class, NewsEntity::class, NewsFavEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun healthDao(): HealthDao

    companion object {

        @Volatile
        private var INSTANCE: HealthDatabase? = null

        fun getInstance(context: Context): HealthDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    HealthDatabase::class.java,
                    "health.db"
                ).build().apply { INSTANCE = this }
            }
    }
}