package com.example.data.db

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class DbServiceImplTest {

    private val dao: HoldingDao = mockk(relaxed = true)
    private lateinit var dbService: DbService

    @Before
    fun setup() {
        dbService = DbServiceImpl(dao)
    }

    @Test
    fun `when insertHoldingList is called, then DAO's insertHoldingList is invoked with the same list`() = runTest {
        val holdings = listOf(HoldingEntity("ICICI", 100, 101.0, 95.0, 98.0))

        dbService.insertHoldingList(holdings)

        coVerify { dao.insertHoldingList(holdings) }
    }

    @Test
    fun `when getHoldingList is called, then it returns the list provided by DAO's getHoldingList`() = runTest {
        val expected = listOf(HoldingEntity("ICICI", 199, 96.0, 95.0, 98.0))
        coEvery { dao.getHoldingList() } returns expected

        val result = dbService.getHoldingList()

        assert(result == expected)
        coVerify { dao.getHoldingList() }
    }

    @After
    fun tearDown() {
        clearAllMocks()
        unmockkAll()
    }
}
