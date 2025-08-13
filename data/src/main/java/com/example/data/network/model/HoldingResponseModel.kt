package com.example.data.network.model

import com.google.gson.annotations.SerializedName

 class HoldingResponseModel(
    @SerializedName("data")
    val data: Data
) {
     class Data(
        @SerializedName("userHolding")
        val userHolding: List<HoldingDto>
    )
}