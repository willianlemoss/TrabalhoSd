package com.example.demo.event

import com.example.demo.doMain.Coordinate
import com.example.demo.doMain.Event
import com.example.demo.doMain.Route
import com.example.demo.doMain.Stop
import com.example.demo.doMain.enum.EventType
import com.example.demo.repository.EventRepository
import com.example.demo.repository.RouteRepository
import com.example.demo.repository.StopRepository
import com.example.demo.stream.NotificationDto
import com.example.demo.stream.geoFence
import com.example.demo.util.haversineDistance
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import java.util.*

@Component
class EventArrival(
        private  val routeRepository: RouteRepository,
        private  val eventRepository: EventRepository,
) {

    private val log = LoggerFactory.getLogger(EventArrival::class.java)

    fun processCoordinate(notificationDto: NotificationDto) {
        val lastCoordinate = notificationDto.lastCoordinate
        val coordinate = notificationDto.coordinate
        val isSamePlace =
            (lastCoordinate.latitude == coordinate.latitude && lastCoordinate.longitude == coordinate.longitude)
        if (isSamePlace) {

            routeRepository.getRouteByEquipment_Id(coordinate.equipmentId)
                .map { route -> Pair(filterListStops(coordinate, route.stops).toFlux(), route) }
                .flatMap { pair ->
                        pair.first.flatMap { stop ->
                            arrivedStopOnRoute(pair.second, stop)
                                .flatMap {
                                    registerEvent(EventType.CHEGADA, stop.id)
                                }
                        }.then()
                }.subscribe()
        }
    }

    private fun arrivedStopOnRoute(route: Route, oldStop: Stop): Mono<Route>{
        val updatedStop = oldStop.copy(arrivalAt = Date())
        val indexOf = route.stops.indexOf(oldStop)
        route.stops.removeAt(indexOf)
        route.stops.add(updatedStop)
        return routeRepository.save(route)
    }

    private fun registerEvent(eventType: EventType, stopId: Int): Mono<Event> {
        log.info("Driver arrive at stop {}", stopId)
        val newEvent = Event(id = null, eventType, Date(), stopId)
        return eventRepository.save(newEvent)
    }

    private fun filterListStops(coordinate: Coordinate, stops: List<Stop>) = stops.filter { stop ->
        stop.arrivalAt == null &&
                haversineDistance(coordinate.latitude, coordinate.longitude, stop.latitude, stop.longitude) <= geoFence
    }
}