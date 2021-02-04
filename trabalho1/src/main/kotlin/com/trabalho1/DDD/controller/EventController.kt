package com.trabalho1.DDD.controller

import com.trabalho1.DDD.doMain.Event
import com.trabalho1.DDD.repository.EventRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/events")
class EventController(
        private val eventRepository: EventRepository
){
    @GetMapping()
    fun getRoute(): ResponseEntity<List<Event>>{
        return  ResponseEntity(eventRepository.findAll(), HttpStatus.OK)
    }
}