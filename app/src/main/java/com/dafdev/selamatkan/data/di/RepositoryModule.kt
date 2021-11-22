package com.dafdev.selamatkan.data.di

import com.dafdev.selamatkan.data.repository.HealthRepository
import com.dafdev.selamatkan.data.repository.IHealthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(healthRepository: HealthRepository): IHealthRepository
}