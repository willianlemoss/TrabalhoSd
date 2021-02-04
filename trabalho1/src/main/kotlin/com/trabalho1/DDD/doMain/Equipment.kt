package com.trabalho1.DDD.doMain

import javax.persistence.Entity
import javax.persistence.Id


@Entity
data class Equipment (
    @Id
    val id: Int,
    val name: String? = null
)