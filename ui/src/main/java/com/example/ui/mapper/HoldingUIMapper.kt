package com.example.ui.mapper

import com.example.domain.model.Holding
import com.example.domain.util.formatAmount
import com.example.domain.util.getFormattedString
import com.example.domain.util.orZero
import com.example.domain.util.roundToTwoDecimals
import com.example.ui.model.HoldingUIModel
import javax.inject.Inject

class HoldingUIMapper @Inject constructor() {

    private fun toUIModel(holding: Holding): HoldingUIModel {
        val pnl = (holding.ltp.orZero() - holding.avgPrice.orZero()) * holding.quantity
        return HoldingUIModel(
            holding = holding,
            individualStockPNL = pnl,
            formattedLTP = holding.ltp.toString().formatAmount(),
            formattedPNL = pnl.roundToTwoDecimals().getFormattedString()
        )
    }

    fun toUIModelList(holdings: List<Holding>) = holdings.map { toUIModel(it) }

}
