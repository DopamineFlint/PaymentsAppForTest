package com.example.paymentsappfortest.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
        val success: Boolean,
        @SerializedName("response") val tokenResponse: TokenResponse
        )
