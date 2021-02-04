package com.trabalho1.DDD.doMain

import java.util.*

data class CoordinateMobile (
    val equipmentMobileId: Int,
    val latitude: Double,
    val longitude: Double,
    val datePing: Date? = null
)