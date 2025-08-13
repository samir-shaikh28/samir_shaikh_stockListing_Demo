package com.example.data.db

interface DbService {

    suspend fun insertHoldingList(countries: List<HoldingEntity>)

    suspend fun getHoldingList(): List<HoldingEntity>
}