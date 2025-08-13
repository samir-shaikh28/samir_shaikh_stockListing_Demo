package com.example.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screen.HoldingScreen
import com.example.ui.theme.Samir_Shaikh_DemoTheme
import com.example.ui.viewmodel.HoldingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Samir_Shaikh_DemoTheme {
                val viewModel = hiltViewModel<HoldingsViewModel>()
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                HoldingScreen(screenState = state) {
                    viewModel.fetchHoldings()
                }
            }
        }
    }
}