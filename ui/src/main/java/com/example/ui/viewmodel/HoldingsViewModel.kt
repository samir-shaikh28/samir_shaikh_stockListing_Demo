package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetHoldingsUseCase
import com.example.ui.mapper.HoldingUIMapper
import com.example.ui.model.HoldingSummaryUI
import com.example.ui.state.HoldingScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val getHoldingsUseCase: GetHoldingsUseCase,
    private val mapper: HoldingUIMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow<HoldingScreenUiState>(HoldingScreenUiState.Loading)
    val uiState: StateFlow<HoldingScreenUiState> = _uiState

    init {
        fetchHoldings()
    }
    
    fun fetchHoldings() {
        _uiState.update { HoldingScreenUiState.Loading }
        viewModelScope.launch {
            getHoldingsUseCase().collect { result ->
                result.onSuccess { holdingSummary ->

                    val uiSummary = HoldingSummaryUI(
                        holdingList = mapper.toUIModelList(holdingSummary.holdingsList),
                        investmentInfo = holdingSummary.investmentInfo
                    )
                    _uiState.update { HoldingScreenUiState.Success(summary = uiSummary) }
                }.onFailure { error ->
                    _uiState.update { HoldingScreenUiState.Error(error.message ?: "Something went wrong") }
                }
            }
        }
    }
}
