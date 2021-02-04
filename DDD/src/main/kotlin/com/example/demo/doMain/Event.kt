package com.example.demo.doMain


import com.example.demo.doMain.enum.EventType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "event")
data class Event(
        @Id val id: String? = null,
        val eventType: EventType,
        val `when`: Date,
        val stopId: Int? = null,
        val latidude: Double? = null,
        val longitude: Double? = null
)