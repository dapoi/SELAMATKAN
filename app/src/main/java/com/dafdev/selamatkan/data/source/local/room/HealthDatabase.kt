package com.dafdev.selamatkan.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dafdev.selamatkan.data.source.local.model.CitiesEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity

@Database(
    entities = [ProvinceEntity::class, CitiesEntity::class],
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
                    context,
                    HealthDatabase::class.java,
                    "health.db"
                ).fallbackToDestructiveMigration().build().apply {
                    INSTANCE = this
                }
            }
    }
}