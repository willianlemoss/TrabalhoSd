package com.example.demo.repository

import com.example.demo.doMain.Stop
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface StopRepository: ReactiveMongoRepository<Stop, String>