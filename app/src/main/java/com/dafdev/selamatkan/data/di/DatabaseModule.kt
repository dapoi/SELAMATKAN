package com.dafdev.selamatkan.data.di

import android.content.Context
import androidx.room.Room
import com.dafdev.selamatkan.data.source.local.room.HealthDao
import com.dafdev.selamatkan.data.source.local.room.HealthDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun getInstance(@ApplicationContext context: Context): HealthDatabase = Room.databaseBuilder(
        context, HealthDatabase::class.java, "SELAMATKAN.db"
    ).build()

    @Provides
    fun healthDao(database: HealthDatabase): HealthDao = database.healthDao()
}