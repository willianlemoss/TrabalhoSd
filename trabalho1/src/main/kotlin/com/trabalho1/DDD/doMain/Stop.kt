package com.trabalho1.DDD.doMain

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Stop (
        @Id
        val id: Int,
        val latitude: Double,
        val longitude: Double,
        val arrivalAt: Date? = null,
        val departuredAt: Date? = null,
        val address: String

)