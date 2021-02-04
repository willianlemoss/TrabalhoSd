package com.example.demo.doMain

import com.example.demo.doMain.enum.FazendoType


data class EquipmentMobile (
        val id: Int,
        val name: String? = null,
        val fazendoType : FazendoType
)