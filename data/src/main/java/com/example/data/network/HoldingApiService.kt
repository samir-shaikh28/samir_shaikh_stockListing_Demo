package com.example.data.network

import com.example.data.network.model.HoldingResponseModel
import retrofit2.http.GET

interface HoldingApiService {
    @GET("/")
    suspend fun getHoldingResponse(): HoldingResponseModel
}
