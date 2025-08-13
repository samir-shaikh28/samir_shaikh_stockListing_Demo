package com.example.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.Holding
import com.example.domain.model.InvestmentInfo
import com.example.ui.R
import com.example.ui.component.LoadingIndicator
import com.example.ui.component.NoDataScreen
import com.example.ui.component.ProfitLossBottomSheetInfo
import com.example.ui.component.StockItem
import com.example.ui.mapper.HoldingUIMapper
import com.example.ui.model.HoldingSummaryUI
import com.example.ui.model.HoldingUIModel
import com.example.ui.state.HoldingScreenUiState
import com.example.ui.theme.PlasMid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoldingScreen(
    screenState: HoldingScreenUiState,
    onRefresh: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.holdings), color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = PlasMid
                )
            )
        },
        bottomBar = {
            (screenState as? HoldingScreenUiState.Success)
                ?.summary
                ?.let { HoldingBottomBar(it.investmentInfo) }

        },
        content = { paddingValues ->
            when (screenState) {
                is HoldingScreenUiState.Loading -> LoadingIndicator(paddingValues)
                is HoldingScreenUiState.Success -> HoldingList(
                    items = screenState.summary.holdingList,
                    paddingValues = paddingValues
                )
                is HoldingScreenUiState.Error -> NoDataScreen(
                    message = screenState.message,
                    paddingValues = paddingValues,
                    onRefresh = onRefresh
                )
            }
        })
}

@Composable
fun HoldingBottomBar(bottomInfo: InvestmentInfo?) {
    bottomInfo?.let {
        val shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape),
            shape = shape,
            tonalElevation = 1.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            ProfitLossBottomSheetInfo(
                modifier = Modifier.fillMaxWidth(),
                detailInfo = bottomInfo
            )
        }
    }
}

@Composable
fun HoldingList(items: List<HoldingUIModel>, paddingValues: PaddingValues) {
    if (items.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
        ) {
            itemsIndexed(items) { index, item ->
                StockItem(
                    modifier = Modifier.padding(16.dp),
                    uiModel = item
                )
                if (index != items.lastIndex)
                    HorizontalDivider(modifier = Modifier.background(color = Color.Gray))
            }
        }

    }
}

@Preview
@Composable
fun PreviewHoldingScreen() {
    HoldingScreen(
        screenState = HoldingScreenUiState.Success(
            summary = HoldingSummaryUI(
                holdingList = HoldingUIMapper().toUIModelList(sampleHoldings),
                investmentInfo = InvestmentInfo(
                    currentValue = 2979507.0,
                    totalInvestment = 2906545.95,
                    todaysPNL = -31841.15,
                    totalPNL = 72961.05,
                    percentageChange = 77.44
                )
            )
        ),
        onRefresh = {}
    )
}

val sampleHoldings = listOf(
    Holding("MAHABANK", 990, 38.05, 35.0, 40.0),
    Holding("ICICI", 100, 118.25, 110.0, 105.0),
    Holding("SBI", 150, 550.05, 501.0, 590.0),
    Holding("TATA STEEL", 200, 137.0, 110.65, 100.05),
    Holding("INFOSYS", 121, 1305.0, 1245.45, 1103.85),
    Holding("AIRTEL", 415, 340.75, 370.1, 290.0),
    Holding("UCO BANK", 2000, 18.05, 28.15, 22.25),
    Holding("NHPC", 900, 88.05, 80.75, 70.65),
    Holding("SJVN", 400, 113.05, 105.0, 110.0),
    Holding("PNB BANK", 100, 132.05, 100.0, 145.55),
    Holding("RELIANCE", 50, 2500.0, 2450.0, 2600.0),
    Holding("HDFC", 75, 1800.25, 1750.0, 1700.0),
    Holding("MARUTI", 30, 7000.0, 6800.0, 7200.0),
    Holding("TCS", 150, 3500.0, 3400.0, 3300.0),
    Holding("HCL", 200, 1000.0, 980.0, 1050.0),
    Holding("WIPRO", 300, 500.0, 480.0, 520.0),
    Holding("BPCL", 80, 400.0, 380.0, 420.0),
    Holding("HPCL", 60, 300.0, 290.0, 320.0),
    Holding("ONGC", 120, 150.0, 140.0, 160.0),
    Holding("IOC", 200, 120.0, 110.0, 130.0),
    Holding("HINDALCO", 150, 400.0, 380.0, 420.0),
    Holding("ADANI PORTS", 500, 800.0, 780.0, 820.0),
    Holding("CIPLA", 100, 900.0, 880.0, 920.0),
    Holding("JSW STEEL", 250, 600.0, 580.0, 620.0),
    Holding("AXIS BANK", 300, 700.0, 680.0, 720.0)
)