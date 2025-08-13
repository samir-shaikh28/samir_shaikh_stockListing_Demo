package com.example.ui.model

import com.example.domain.model.InvestmentInfo

data class HoldingSummaryUI(
    val holdingList: List<HoldingUIModel>,
    val investmentInfo: InvestmentInfo?
)