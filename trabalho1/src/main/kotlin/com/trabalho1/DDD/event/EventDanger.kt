package com.trabalho1.DDD.event

import com.trabalho1.DDD.doMain.Event
import com.trabalho1.DDD.doMain.enum.EventType
import com.trabalho1.DDD.doMain.enum.FazendoType
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
class EventDanger(
        private  val routeRepository: RouteRepository,
        private  val eventRepository: EventRepository,
): Observer {

    private  val log = LoggerFactory.getLogger(EventDanger::class.java)

    fun processCoordinate(notificationDto: NotificationDto){
        val coordinate = notificationDto.coordinate
        val coordinateMobile = notificationDto.coordinateMobile
        val lastCoordinate = notificationDto.lastCoordinate

        if(lastCoordinate.route.equipmentMobile.fazendoType == FazendoType.DIRIGINDO && lastCoordinate.route.dateDanger != null){
            if(coordinate.latitude != coordinateMobile.latitude || coordinate.longitude != coordinateMobile.longitude){
                val date = Date()
                if (date.time - lastCoordinate.route.dateDanger.time > 600000){
                    val newEvent = Event(id = null, EventType.PERIGO, date, latidude = coordinateMobile.latitude, longitude = coordinateMobile.longitude)
                    eventRepository.save(newEvent)
                    log.info("Event received danger {}", newEvent)
                    val newRoute = lastCoordinate.route.copy(dateDanger = Date())
                    routeRepository.save(newRoute)
                }
            }else{
                val newRoute = lastCoordinate.route.copy(dateDanger = Date())
                routeRepository.save(newRoute)
            }
        }else{
            val newRoute = lastCoordinate.route.copy(dateDanger = Date())
            routeRepository.save(newRoute)

        }
    }

    override fun update(p0: Observable?, notificationDto: Any?) {
        if(p0 != null && notificationDto != null ){
            processCoordinate(notificationDto as NotificationDto)
        }
    }
}