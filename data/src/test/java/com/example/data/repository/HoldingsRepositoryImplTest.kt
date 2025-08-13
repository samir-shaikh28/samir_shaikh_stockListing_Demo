package com.example.data.repository

import com.example.data.db.DbService
import com.example.data.db.HoldingEntity
import com.example.data.network.HoldingApiService
import com.example.domain.repository.HoldingsRepository
import com.example.data.network.model.HoldingDto
import com.example.data.network.model.HoldingResponseModel
import com.example.data.utils.MainDispatcherRule
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class HoldingsRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val apiService: HoldingApiService = mockk()
    private val databaseService: DbService = mockk()
    private lateinit var repository: HoldingsRepository

    @Before
    fun setUp() {
        repository =
            HoldingsRepositoryImpl(apiService, databaseService, mainDispatcherRule.testDispatcher)
    }

    @Test
    fun `getHoldings WHEN data exists in database THEN emits cached data AND does not call API`() = runTest {
        val cachedEntities = listOf(
            HoldingEntity("ITC", 10, 100.0, 95.0, 98.0)
        )
        coEvery { databaseService.getHoldingList() } returns cachedEntities

        // Act
        val result = repository.getHoldings().first()

        // Assert
        assert(result.isSuccess)
        val data = result.getOrNull()
        assert(data?.first()?.symbol == "ITC")
        coVerify(exactly = 1) { databaseService.getHoldingList() }
        coVerify(exactly = 0) { apiService.getHoldingResponse() } // API should not be called
    }

    @Test
    fun `getHoldings WHEN no data in database AND API call succeeds THEN fetches from API, caches, AND emits new data`() = runTest {
        coEvery { databaseService.getHoldingList() } returns emptyList()

        val dto = HoldingDto("ITC", 10, 100.0, 95.0, 98.0)
        val response = HoldingResponseModel(data = HoldingResponseModel.Data(listOf(dto)))

        coEvery { apiService.getHoldingResponse() } returns response
        coEvery { databaseService.insertHoldingList(any()) } just Runs

        // Act
        val result = repository.getHoldings().first()

        // Assert
        assert(result.isSuccess)
        val list = result.getOrNull()
        assert(list?.first()?.symbol == "ITC")

        coVerifySequence {
            databaseService.getHoldingList()
            apiService.getHoldingResponse()
            databaseService.insertHoldingList(match { it.size == 1 })
        }
    }

    @Test
    fun `getHoldings WHEN no data in database AND API call fails with IOException THEN emits failure`() = runTest {
        coEvery { databaseService.getHoldingList() } returns emptyList()
        coEvery { apiService.getHoldingResponse() } throws IOException("Network error")

        // Act
        val result = repository.getHoldings().first()

        // Assert
        assert(result.isFailure)
        assert(result.exceptionOrNull() is IOException)

        coVerifySequence {
            databaseService.getHoldingList()
            apiService.getHoldingResponse()
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
        unmockkAll()
    }
}
