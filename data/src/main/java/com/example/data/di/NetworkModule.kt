package com.example.data.di

import com.google.gson.GsonBuilder
import com.example.data.BuildConfig
import com.example.data.network.HoldingApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideClientConfig(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().setLenient().create())
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        client: OkHttpClient,
        gsonConverter: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverter)
            .build()
    }

    @Provides
    @Singleton
    fun provideHoldingApiService(
        retrofit: Retrofit
    ): HoldingApiService {
        return retrofit.create(HoldingApiService::class.java)
    }

}