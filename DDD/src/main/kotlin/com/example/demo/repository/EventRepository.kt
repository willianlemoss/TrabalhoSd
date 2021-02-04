package com.example.demo.repository


import com.example.demo.doMain.Event
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository: ReactiveMongoRepository<Event, String>