package com.example.data.di

import com.example.data.db.DbService
import com.example.data.db.DbServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DatabaseServiceModule {

    @Binds
    @Singleton
    fun bindDatabaseService(impl: DbServiceImpl): DbService
}