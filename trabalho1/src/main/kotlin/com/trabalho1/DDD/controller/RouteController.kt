package com.trabalho1.DDD.controller

import com.trabalho1.DDD.doMain.Route
import com.trabalho1.DDD.repository.RouteRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/routes")
class RouteController(
        private val routeRepository: RouteRepository
){

    @PostMapping()
    fun createRoute(@RequestBody route: Route){
        routeRepository.save(route)
    }

    @DeleteMapping()
    fun deleteRoute(@PathVariable id: Int){
        routeRepository.deleteById(id)
    }

    @GetMapping()
    fun getRoute(): ResponseEntity<List<Route>>{
        return  ResponseEntity(routeRepository.findAll(), HttpStatus.OK)
    }
}