package com.dafdev.selamatkan.data.di

import com.dafdev.selamatkan.data.domain.usecase.HealthInteractor
import com.dafdev.selamatkan.data.domain.usecase.HealthUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideHealthUseCase(healthInteractor: HealthInteractor): HealthUseCase
}