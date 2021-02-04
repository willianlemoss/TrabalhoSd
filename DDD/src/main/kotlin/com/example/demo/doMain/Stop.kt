package com.example.demo.doMain

import java.util.*

data class Stop (
        val id: Int,
        val latitude: Double,
        val longitude: Double,
        val arrivalAt: Date? = null,
        var departuredAt: Date? = null,
        var address: String

)