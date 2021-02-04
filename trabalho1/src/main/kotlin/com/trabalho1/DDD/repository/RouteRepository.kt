package com.trabalho1.DDD.repository

import com.trabalho1.DDD.doMain.Route
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RouteRepository: JpaRepository<Route, Int>{

    fun getRouteByEquipment_Id(id: Int): Optional<Route>

}