package com.example.demo.event

import com.example.demo.doMain.Event
import com.example.demo.doMain.Route
import com.example.demo.doMain.enum.EventType
import com.example.demo.doMain.enum.FazendoType
import com.example.demo.repository.EventRepository
import com.example.demo.repository.RouteRepository
import com.example.demo.stream.NotificationDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class EventDanger(
        private  val routeRepository: RouteRepository,
        private  val eventRepository: EventRepository,
){

    private  val log = LoggerFactory.getLogger(EventDanger::class.java)

    fun processCoordinate(notificationDto: NotificationDto){
        val coordinate = notificationDto.coordinate
        val coordinateMobile = notificationDto.coordinateMobile


        routeRepository.getRouteByEquipment_Id(coordinate.equipmentId)
            .map { route ->
                log.info("Event received danger {}", coordinate)
                log.info("Event received danger {}", coordinateMobile)
                log.info("Event received danger {}", route)

                if(route.equipmentMobile.fazendoType == FazendoType.DIRIGINDO && route.dateDanger != null &&
                    ( coordinate.latitude != coordinateMobile.latitude || coordinate.longitude != coordinateMobile.longitude ) ){
                    log.info("Event received danger if")
                    val date = Date()
                        if (date.time - route.dateDanger.time > 600000){
                            val newEvent = Event(id = null, EventType.PERIGO, date, latidude = coordinateMobile.latitude, longitude = coordinateMobile.longitude)
                            eventRepository.save(newEvent)
                            log.info("Event received danger {}", newEvent)
                            registerRoute(route)
                        }
                }else{
                    log.info("Event received danger Else",)
                    registerRoute(route)
                }
            }
            .subscribe()

    }

    fun registerRoute(route: Route){
    // não está atualizando o valor do dateDanger
        val newRoute = route.copy(id= route.id, dateDanger = Date())
        log.info("Event received danger {}", newRoute)
        routeRepository.save(newRoute)
    }

}
