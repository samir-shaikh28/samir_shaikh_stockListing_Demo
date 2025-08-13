package com.example.data.db

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbServiceImpl @Inject constructor(
    private val dao: HoldingDao
) : DbService {
    override suspend fun insertHoldingList(countries: List<HoldingEntity>) {
        dao.insertHoldingList(countries)
    }

    override suspend fun getHoldingList(): List<HoldingEntity> {
       return dao.getHoldingList()
    }
}