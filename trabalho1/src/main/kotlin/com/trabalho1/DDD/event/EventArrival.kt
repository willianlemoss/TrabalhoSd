package com.trabalho1.DDD.event

import com.trabalho1.DDD.doMain.Event
import com.trabalho1.DDD.doMain.enum.EventType
import com.trabalho1.DDD.repository.EventRepository
import com.trabalho1.DDD.repository.RouteRepository
import com.trabalho1.DDD.repository.StopRepository
import com.trabalho1.DDD.stream.NotificationDto
import com.trabalho1.DDD.stream.geoFence
import com.trabalho1.DDD.util.haversineDistance
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class EventArrival(
        private  val routeRepository: RouteRepository,
        private val stopRepository: StopRepository,
        private  val eventRepository: EventRepository,
): Observer {

    private  val log = LoggerFactory.getLogger(EventArrival::class.java)

    fun processCoordinate(notificationDto: NotificationDto){
        val lastCoordinate = notificationDto.lastCoordinate
        val coordinate = notificationDto.coordinate

        val isSamePlace = (lastCoordinate.latitude == coordinate.latitude && lastCoordinate.longitude == coordinate.longitude)

        if(isSamePlace){

            val route = routeRepository.getRouteByEquipment_Id(coordinate.equipmentId).get()
            route.stops.filter { stop ->
                stop.arrivalAt == null &&
                        haversineDistance(coordinate.latitude, coordinate.longitude, stop.latitude, stop.longitude) <= geoFence
            }.map {
                val updatedStop = it.copy(arrivalAt =  coordinate.datePing)
                stopRepository.save(updatedStop)
                log.info("Driver arrive at stop {}", it)
                val newEvent = Event(id = null, EventType.CHEGADA, Date(), it.id)
                log.info("Driver arrive at stop {}", newEvent.`when`.time)
                eventRepository.save(newEvent)
            }
        }
    }

    override fun update(p0: Observable?, notificationDto: Any?) {
        if(p0 != null && notificationDto != null){
            processCoordinate(notificationDto as NotificationDto)
        }
    }
}