package com.example.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.model.Holding
import com.example.domain.model.InvestmentInfo
import com.example.domain.util.getFormattedString
import com.example.ui.component.InvestmentDetails
import com.example.ui.component.NoDataScreen
import com.example.ui.component.ProfitLossBottomSheetInfo
import com.example.ui.component.StockItem
import com.example.ui.model.HoldingSummaryUI
import com.example.ui.model.HoldingUIModel
import com.example.ui.model.InvestmentDataItem
import com.example.ui.state.HoldingScreenUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HoldingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyHoldingList = listOf(
        HoldingUIModel(
            holding = Holding(
                "ITC", 10, 100.0, 95.0, 98.0
            ),
            individualStockPNL = 5727.0, "₹ 11111.50", "₹ 22222.50"
        ),
        HoldingUIModel(
            holding = Holding(
                "PGEL", 10, 100.0, 95.0, 98.0
            ),
            individualStockPNL = 5727.0, "₹ 11111.50", "₹ 22222.50"
        )
    )

    private val investmentInfo = InvestmentInfo(
        currentValue = 50000.0,
        totalInvestment = 45000.0,
        todaysPNL = 200.0,
        totalPNL = 50000.0,
        percentageChange = 11.11
    )

    @Test
    fun whenScreenStateIsLoading_thenShowLoadingIndicator() {
        composeTestRule.setContent {
            HoldingScreen(screenState = HoldingScreenUiState.Loading, onRefresh = {})
        }
        composeTestRule
            .onNodeWithTag("loading_indicator")
            .assertIsDisplayed()
    }

    @Test
    fun whenScreenStateIsSuccess_thenShowList() {

        val holdingSummaryUI = HoldingSummaryUI(
            holdingList = dummyHoldingList,
            investmentInfo = investmentInfo
        )
        val state = HoldingScreenUiState.Success(
            holdingSummaryUI
        )

        composeTestRule.setContent {
            HoldingScreen(screenState = state, onRefresh = {})
        }

        // Check if first item exists
        composeTestRule.onNodeWithText("ITC").assertIsDisplayed()
        composeTestRule.onNodeWithText("PGEL").assertIsDisplayed()
    }

    @Test
    fun profitLossBottomBar_showsAlwaysVisiblePNL() {
        val info = InvestmentInfo(
            currentValue = 200000.0,
            totalInvestment = 150000.0,
            todaysPNL = 2000.0,
            totalPNL = 50000.0,
            percentageChange = 10.0
        )

        composeTestRule.setContent {
            HoldingBottomBar(bottomInfo = info)
        }

        //composeTestRule.onRoot().printToLog("holding_test")

        composeTestRule.onNodeWithText("50,000", substring = true).assertExists()
    }


    @Test
    fun whenScreenStateIsError_thenShowNoDataScreen() {
        composeTestRule.setContent {
            HoldingScreen(
                screenState = HoldingScreenUiState.Error(),
                onRefresh = {})
        }

        composeTestRule
            .onNodeWithText("No data available")
            .assertIsDisplayed()
    }

    @Test
    fun stockItemDisplaysCorrectData() {
        val model = HoldingUIModel(
            holding = Holding("RELIANCE", 20, 30.0, 40.0, 50.0),
            formattedLTP = "₹2450",
            formattedPNL = "+₹1500",
            individualStockPNL = 1500.0
        )

        composeTestRule.setContent {
            StockItem(uiModel = model)
        }

        composeTestRule.onNodeWithText("RELIANCE").assertIsDisplayed()
        composeTestRule.onNodeWithText("₹2450").assertIsDisplayed()
        composeTestRule.onNodeWithText("+₹1500").assertIsDisplayed()
        composeTestRule.onNodeWithText("20").assertIsDisplayed()
    }

    @Test
    fun noDataScreenShowsMessageAndRefreshButton() {
        var refreshClicked = false

        composeTestRule.setContent {
            NoDataScreen(paddingValues = PaddingValues(), onRefresh = { refreshClicked = true })
        }

        composeTestRule.onNodeWithText("No data available").assertIsDisplayed()
        composeTestRule.onNodeWithText("Refresh").performClick()
        assert(refreshClicked)
    }

    @Test
    fun investmentDetailsShowsDataAndHandlesIconClick() {
        var clicked = false

        composeTestRule.setContent {
            InvestmentDetails(
                item = InvestmentDataItem(
                    "Current Value*",
                    investmentInfo.currentValue.getFormattedString()
                ),
                shouldShowIcon = true,
                onPnLClicked = { clicked = true }
            )
        }

        composeTestRule.onNode(hasClickAction()).performClick()
        assert(clicked)
    }

    @Test
    fun profitLossBottomSheetDisplaysInfoAndTogglesVisibility() {
        composeTestRule.setContent {
            ProfitLossBottomSheetInfo(detailInfo = investmentInfo)
        }

        // P&L should be visible always
        composeTestRule.onNodeWithText("Profit & Loss*").assertIsDisplayed()

        // Expandable section is initially collapsed
        composeTestRule.onNodeWithTag("expanded_sheet").assertDoesNotExist()

        // Perform click to expand
        composeTestRule.onNode(hasClickAction()).performClick()

        // Now the expandable section should be visible
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("expanded_sheet").assertIsDisplayed()

        // Perform click to collapse again
        composeTestRule.onNode(hasClickAction()).performClick()

        // Should disappear again
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("expanded_sheet").assertIsNotDisplayed()
    }
}