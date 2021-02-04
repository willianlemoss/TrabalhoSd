package com.example.demo.controller

import com.example.demo.doMain.Route
import com.example.demo.repository.RouteRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/routes")
class RouteController(
        private val routeRepository: RouteRepository
){

    @PostMapping()
    fun createRoute(@RequestBody route: Route): Mono<Route> {
        return routeRepository.save(route)
    }

    @DeleteMapping()
    fun deleteRoute(@PathVariable id: Int): Mono<Void> {
       return routeRepository.deleteById(id)
    }

    @GetMapping()
    fun getRoute(): Flux<Route> {
        return routeRepository.findAll()
    }
}