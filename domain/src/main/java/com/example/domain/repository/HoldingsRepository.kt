package com.example.domain.repository

import com.example.domain.model.Holding
import kotlinx.coroutines.flow.Flow

interface HoldingsRepository {
    fun getHoldings(): Flow<Result<List<Holding>>>
}