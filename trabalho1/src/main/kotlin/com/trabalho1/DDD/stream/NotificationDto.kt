package com.trabalho1.DDD.stream

import com.trabalho1.DDD.doMain.Coordinate
import com.trabalho1.DDD.doMain.CoordinateMobile
import com.trabalho1.DDD.doMain.LastCoordinate

data class NotificationDto (
        val coordinate: Coordinate,
        val coordinateMobile: CoordinateMobile,
        val lastCoordinate: LastCoordinate
)