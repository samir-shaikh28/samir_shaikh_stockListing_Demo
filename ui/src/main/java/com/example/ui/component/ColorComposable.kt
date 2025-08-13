package com.example.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.GreenC
import com.example.ui.theme.RedC

@Composable
fun ColorComposable(value: Double): Color {
    return if (value > 0) {
        GreenC
    } else {
        RedC
    }
}