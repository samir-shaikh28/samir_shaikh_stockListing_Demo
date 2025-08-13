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

fun Double?.getFormattedString(): String {
    return if (this != null)
        buildString {
            append(StringConstants.INRSymbol)
            append(" ")
        }.plus(this.toString().formatAmount())
    else ""
}

fun String.formatAmount(): String {
    val formatter = if (this.contains(".")) {
        DecimalFormat("##,##,##,###.${this.decimalPattern()}")
    } else {
        DecimalFormat("##,##,##,###")
    }
    return formatter.format(this.toDoubleOrNull() ?: 0)
}


fun String.decimalPattern(): String {
    val decimalCount = this.length - 1 - this.indexOf(".")
    val decimalPattern = StringBuilder()
    var i = 0
    while (i < decimalCount && i < 2) {
        decimalPattern.append("0")
        i++
    }
    return decimalPattern.toString()
}