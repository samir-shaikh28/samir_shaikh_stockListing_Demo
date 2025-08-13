package com.example.domain.mapper

import com.example.domain.model.Holding
import com.example.domain.model.HoldingSummary
import com.example.domain.model.InvestmentInfo
import com.example.domain.util.roundToTwoDecimals
import com.example.domain.util.orZero

class HoldingSummaryMapper {

    fun map(holdingList: List<Holding>): HoldingSummary {
        val currentValue = getCurrentValue(holdingList)
        val totalInvestment = getTotalInvestment(holdingList)

        val investmentInfo = InvestmentInfo(
            currentValue = currentValue,
            totalInvestment = totalInvestment,
            totalPNL = currentValue - totalInvestment,
            todaysPNL = getDayPnL(holdingList),
            percentageChange = getPercentageChange(holdingList)
        )

        return HoldingSummary(holdingsList = holdingList, investmentInfo = investmentInfo)
    }

    fun getCurrentValue(holdingList: List<Holding>) =
        holdingList.sumOf { getCurrentValue(it) }


    fun getTotalInvestment(holdingList: List<Holding>) =
        holdingList.sumOf { getInvestmentValue(it) }


    fun getCurrentValue(holding: Holding) =
        holding.ltp.orZero() * holding.quantity.orZero()


    fun getInvestmentValue(holding: Holding?) =
        holding?.avgPrice?.orZero()?.times(holding.quantity.orZero()).orZero()

    fun getDayPnL(list: List<Holding>) = list.sumOf {
        (it.close.orZero() - it.ltp.orZero()) * it.quantity.orZero()
    }

    fun getTodaysPnl(list: List<Holding>): Double {
        return list.sumOf { (it.ltp.orZero() - it.avgPrice.orZero()) * it.quantity.orZero() }
    }

    fun getClosingPnl(list: List<Holding>): Double {
        return list.sumOf { (it.close.orZero() - it.avgPrice.orZero()) * it.quantity.orZero() }
    }

    fun getPercentageChange(list: List<Holding>): Double {
        val totalPnlToday = getTodaysPnl(list)
        val totalPnlClose = getClosingPnl(list)
        val percentageChange = if (totalPnlClose != 0.0) {
            ((totalPnlToday - totalPnlClose) / totalPnlClose) * 100
        } else {
            0.0
        }
        return percentageChange.roundToTwoDecimals()
    }
}
