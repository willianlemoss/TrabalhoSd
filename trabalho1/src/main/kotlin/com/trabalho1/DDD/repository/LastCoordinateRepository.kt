package com.trabalho1.DDD.repository

import com.trabalho1.DDD.doMain.LastCoordinate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LastCoordinateRepository: JpaRepository<LastCoordinate, Int>{

    fun getLastCoordinateByEquipment_Id(id: Int): Optional<LastCoordinate>

}