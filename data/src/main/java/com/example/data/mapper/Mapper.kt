package com.example.data.mapper

import com.example.data.db.HoldingEntity
import com.example.data.network.model.HoldingDto
import com.example.domain.model.Holding

fun HoldingDto.toDomain(): Holding = Holding(
    symbol = this.symbol,
    quantity = this.quantity,
    ltp = this.ltp,
    avgPrice = this.avgPrice,
    close = this.close
)

fun Holding.toEntity(): HoldingEntity = HoldingEntity(
    symbol = this.symbol,
    quantity = this.quantity,
    ltp = this.ltp,
    avgPrice = this.avgPrice,
    close = this.close
)

fun HoldingEntity.toDomain(): Holding = Holding(
    symbol = this.symbol,
    quantity = this.quantity,
    ltp = this.ltp,
    avgPrice = this.avgPrice,
    close = this.close
)

