package com.example.ui.model

import com.example.domain.model.Holding

data class HoldingUIModel(
    val holding: Holding,
    val individualStockPNL: Double,
    val formattedLTP: String,
    val formattedPNL: String
)