package com.trabalho1.DDD.doMain

import com.trabalho1.DDD.doMain.enum.FazendoType
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class EquipmentMobile (
        @Id
        val id: Int,
        val name: String? = null,
        val fazendoType : FazendoType
)