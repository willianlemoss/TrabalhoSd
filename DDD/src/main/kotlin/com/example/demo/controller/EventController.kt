package com.example.demo.controller

import com.example.demo.doMain.Event
import com.example.demo.repository.EventRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/events")
class EventController(
        private val eventRepository: EventRepository
){
    @GetMapping()
    fun getRoute(): Flux<Event> {
        return  eventRepository.findAll()
    }
}