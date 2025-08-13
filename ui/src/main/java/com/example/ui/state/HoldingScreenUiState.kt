package com.example.ui.state
import com.example.ui.model.HoldingSummaryUI

sealed class HoldingScreenUiState {
    data object Loading : HoldingScreenUiState()
    data class Success(val summary: HoldingSummaryUI) : HoldingScreenUiState()
    data class Error(val message: String ?= null) : HoldingScreenUiState()
}