package com.example.paymentsappfortest.data

import java.util.*

data class Payments(
        val desc: String?,
        val amount: Float,
        val currency: String, //there is a class named Currency. Also currency can be skipped
        val created: Long? //should I use Long here, I think I should use class for time
        )
