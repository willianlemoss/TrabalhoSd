package com.trabalho1.DDD.doMain

import java.util.*
import javax.persistence.*

@Entity
data class Route (
        @Id
        val id: Int,
        val name: String,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "equipment_id", referencedColumnName = "id")
        val equipment: Equipment,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "equipmentMobile_id", referencedColumnName = "id")
        val equipmentMobile: EquipmentMobile,
        @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
        @JoinColumn(name = "stop_id", referencedColumnName = "id")
        val stops: List<Stop>,
        val datePlan: Date,
        val dateDanger: Date? = null,
)