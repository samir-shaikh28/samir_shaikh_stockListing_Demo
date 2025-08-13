package com.example.data.repository

import com.example.data.db.DbService
import com.example.data.di.IoDispatcher
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.network.HoldingApiService
import com.example.domain.model.Holding
import com.example.domain.repository.HoldingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HoldingsRepositoryImpl @Inject constructor(
    private val apiService: HoldingApiService,
    private val databaseService: DbService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : HoldingsRepository {

    override fun getHoldings(): Flow<Result<List<Holding>>> = flow {
        val cached = databaseService.getHoldingList()

        if (cached.isNotEmpty()) {
            emit(Result.success(cached.map { it.toDomain() }))
        } else {
            try {
                val response = apiService.getHoldingResponse()
                val domainList = response.data.userHolding.map { it.toDomain() }


                databaseService.insertHoldingList(domainList.map { it.toEntity() })

                emit(Result.success(domainList))
            } catch (e: Exception) {
                if (e is UnknownHostException) {
                    emit(Result.failure(Throwable("No Internet Connection")))
                } else {
                    emit(Result.failure(e))
                }
            }
        }
    }.flowOn(ioDispatcher)
}


