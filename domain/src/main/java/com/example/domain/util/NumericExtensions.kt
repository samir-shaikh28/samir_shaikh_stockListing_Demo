package com.example.domain.util

import java.math.RoundingMode
import java.text.DecimalFormat

fun Int?.orZero(): Int = this ?: 0

fun Double?.orZero(): Double = this ?: 0.0

fun Double?.roundToTwoDecimals(): Double {
    val df = DecimalFormat("#.00")
    df.roundingMode = RoundingMode.UP
    val roundoff = df.format(this)
    return roundoff.toDouble()
}
