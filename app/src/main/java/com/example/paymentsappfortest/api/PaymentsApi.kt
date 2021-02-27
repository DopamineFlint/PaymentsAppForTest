package com.example.paymentsappfortest.api

import com.example.paymentsappfortest.data.LoginResponse
import com.example.paymentsappfortest.data.PaymentsResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface PaymentsApi {

    companion object {
        const val BASE_URL = "http://82.202.204.94/api/"
    }

    @Multipart
    @Headers("app-key: 12345", "v: 1")
    @POST("login")
    suspend fun login(
            @Part("login") login: RequestBody,
            @Part("password") password: RequestBody
    ): Response<LoginResponse> //Call

    @Headers("app-key: 12345", "v: 1")
    @GET("payments")
    suspend fun getPayments(
            @Query("token")
            paymentToken: Int = 123456789
    ): Response<PaymentsResponse> //Call

}