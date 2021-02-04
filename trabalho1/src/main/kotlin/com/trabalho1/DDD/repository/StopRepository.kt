package com.trabalho1.DDD.repository

import com.trabalho1.DDD.doMain.Stop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StopRepository: JpaRepository<Stop, Int>