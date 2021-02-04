package com.example.demo.repository

import com.example.demo.doMain.Route
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface RouteRepository: ReactiveMongoRepository<Route, Int>{

    fun getRouteByEquipment_Id(id: Int): Mono<Route>

}