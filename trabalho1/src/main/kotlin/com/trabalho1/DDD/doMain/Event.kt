package com.trabalho1.DDD.doMain

import com.trabalho1.DDD.doMain.enum.EventType
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Event(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        val eventType: EventType,
        val `when`: Date,
        val stopId: Int? = null,
        val latidude: Double? = null,
        val longitude: Double? = null
)