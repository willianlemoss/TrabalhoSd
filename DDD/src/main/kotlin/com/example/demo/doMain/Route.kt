package com.example.demo.doMain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "route")
data class Route (
        @Id val id: Int,
        val name: String,
        val equipment: Equipment,
        val equipmentMobile: EquipmentMobile,
        val stops: MutableList<Stop>,
        val datePlan: Date,
        val dateDanger: Date? = null,
)