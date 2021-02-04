package com.example.demo.stream

import com.example.demo.doMain.Coordinate
import com.example.demo.doMain.CoordinateMobile
import com.example.demo.doMain.LastCoordinate

data class NotificationDto (
        val coordinate: Coordinate,
        val coordinateMobile: CoordinateMobile,
        val lastCoordinate: LastCoordinate
)