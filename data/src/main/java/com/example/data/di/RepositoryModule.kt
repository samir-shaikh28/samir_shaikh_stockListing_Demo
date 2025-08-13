package com.example.data.di

import com.example.data.repository.HoldingsRepositoryImpl
import com.example.domain.repository.HoldingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideHoldingRepository(repository: HoldingsRepositoryImpl): HoldingsRepository

}