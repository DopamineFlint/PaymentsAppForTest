package com.example.paymentsappfortest.data

import com.google.gson.annotations.SerializedName

data class PaymentsResponse(
        val success: Boolean,
        @SerializedName("response") val payments: List<Payments>
        )
