package com.example.demo.stream

import com.example.demo.doMain.Coordinate
import com.example.demo.doMain.CoordinateMobile
import com.example.demo.doMain.LastCoordinate
import com.example.demo.event.EventArrival
import com.example.demo.event.EventDanger
import com.example.demo.repository.LastCoordinateRepository
import com.example.demo.repository.RouteRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.Duration
import java.util.*

const val geoFence = 1

@Component
class CoordinateProcessor(
        private  val routeRepository: RouteRepository,
        private val lastCoordinateRepository: LastCoordinateRepository,
        private val eventArrival: EventArrival,
        private val eventDanger: EventDanger
){

    private  val log = LoggerFactory.getLogger(CoordinateProcessor::class.java)

//    fun receivedCoordinate(coordinate: Coordinate, coordinateMobile: CoordinateMobile): Mono<NotificationDto> {
      fun receivedCoordinate(coordinate: Coordinate): Mono<NotificationDto> {
        log.info("Cordinate received {}", coordinate)
//        log.info("Cordinate received {}", coordinateMobile)

        return routeRepository.getRouteByEquipment_Id(coordinate.equipmentId)
            .flatMap { route ->
                lastCoordinateRepository.getLastCoordinateByEquipment_Id(coordinate.equipmentId)
                    .flatMap { lastCoordinate  ->
                        val updateLastCoordinate = lastCoordinate.copy(latitude = coordinate.latitude, longitude = coordinate.longitude, `when`= coordinate.datePing)
                        lastCoordinateRepository.save(updateLastCoordinate)
                    }.switchIfEmpty {
                        val newLastCoordinate = LastCoordinate(_id = null, equipment = route.equipment, coordinate.latitude, coordinate.longitude, routeId = route.id)
                        lastCoordinateRepository.save(newLastCoordinate)
                    }
            }.map { lastCoordinate ->
//                NotificationDto(coordinate, coordinateMobile, lastCoordinate)
                NotificationDto(coordinate, CoordinateMobile(equipmentMobileId = 10000, latitude = 10.0, longitude = 10.0), lastCoordinate)
            }

    }


    @Scheduled(fixedDelay = 100000, initialDelay = 10000)
    fun consumeCoordinates(){
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val jsonContentCoordinate = "[{\"equipmentId\":10000,\"latitude\":-3.752414,\"longitude\":-38.511576,\"datePing\":1599904800000},{\"equipmentId\":10000,\"latitude\":-3.752526,\"longitude\":-38.512504,\"datePing\":1599904920000},{\"equipmentId\":10000,\"latitude\":-3.752581,\"longitude\":-38.513015,\"datePing\":1599905040000},{\"equipmentId\":10000,\"latitude\":-3.75262,\"longitude\":-38.513433,\"datePing\":1599905160000},{\"equipmentId\":10000,\"latitude\":-3.752637,\"longitude\":-38.513635,\"datePing\":1599905220000},{\"equipmentId\":10000,\"latitude\":-3.752637,\"longitude\":-38.513635,\"datePing\":1599905280000},{\"equipmentId\":10000,\"latitude\":-3.752637,\"longitude\":-38.513635,\"datePing\":1599905340000},{\"equipmentId\":10000,\"latitude\":-3.752637,\"longitude\":-38.513635,\"datePing\":1599905400000},{\"equipmentId\":10000,\"latitude\":-3.752696,\"longitude\":-38.513927,\"datePing\":1599905520000},{\"equipmentId\":10000,\"latitude\":-3.7526674,\"longitude\":-38.5149107,\"datePing\":1599905580000},{\"equipmentId\":10000,\"latitude\":-3.7522729,\"longitude\":-38.5162625,\"datePing\":1599905640000},{\"equipmentId\":10000,\"latitude\":-3.751894,\"longitude\":-38.515098,\"datePing\":1599905700000},{\"equipmentId\":10000,\"latitude\":-3.750796,\"longitude\":-38.515302,\"datePing\":1599905760000},{\"equipmentId\":10000,\"latitude\":-3.749769,\"longitude\":-38.515491,\"datePing\":1599905820000},{\"equipmentId\":10000,\"latitude\":-3.7500412,\"longitude\":-38.5161274,\"datePing\":1599905880000},{\"equipmentId\":10000,\"latitude\":-3.7500412,\"longitude\":-38.5161274,\"datePing\":1599905940000},{\"equipmentId\":10000,\"latitude\":-3.7500412,\"longitude\":-38.5161274,\"datePing\":1599906000000},{\"equipmentId\":10000,\"latitude\":-3.7500412,\"longitude\":-38.5161274,\"datePing\":1599906060000},{\"equipmentId\":10000,\"latitude\":-3.7500412,\"longitude\":-38.5161274,\"datePing\":1599906120000},{\"equipmentId\":10000,\"latitude\":-3.7500412,\"longitude\":-38.5161274,\"datePing\":1599906180000},{\"equipmentId\":10000,\"latitude\":-3.748932,\"longitude\":-38.515718,\"datePing\":1599906240000},{\"equipmentId\":10000,\"latitude\":-3.748671,\"longitude\":-38.516117,\"datePing\":1599906300000},{\"equipmentId\":10000,\"latitude\":-3.748641,\"longitude\":-38.516856,\"datePing\":1599906360000},{\"equipmentId\":10000,\"latitude\":-3.74974,\"longitude\":-38.516425,\"datePing\":1599906480000},{\"equipmentId\":10000,\"latitude\":-3.74974,\"longitude\":-38.516425,\"datePing\":1599906540000},{\"equipmentId\":10000,\"latitude\":-3.74974,\"longitude\":-38.516425,\"datePing\":1599906600000},{\"equipmentId\":10000,\"latitude\":-3.74974,\"longitude\":-38.516425,\"datePing\":1599906660000},{\"equipmentId\":10000,\"latitude\":-3.74974,\"longitude\":-38.516425,\"datePing\":1599906720000},{\"equipmentId\":10000,\"latitude\":-3.7506319,\"longitude\":-38.5178648,\"datePing\":1599906960000},{\"equipmentId\":10000,\"latitude\":-3.7506319,\"longitude\":-38.5178648,\"datePing\":1599907020000},{\"equipmentId\":10000,\"latitude\":-3.7506319,\"longitude\":-38.5178648,\"datePing\":1599907080000},{\"equipmentId\":10000,\"latitude\":-3.7506319,\"longitude\":-38.5178648,\"datePing\":1599907140000}]"
        val jsonContentCoordinateMobile = "[{\"equipmentMobileId\":10000,\"latitude\":-3.752415,\"longitude\":-38.511576,\"datePing\":1599904800000},{\"equipmentMobileId\":10000,\"latitude\":-3.752525,\"longitude\":-38.512504,\"datePing\":1599904920000},{\"equipmentMobileId\":10000,\"latitude\":-3.752582,\"longitude\":-38.513015,\"datePing\":1599905040000},{\"equipmentMobileId\":10000,\"latitude\":-3.75261,\"longitude\":-38.513433,\"datePing\":1599905160000},{\"equipmentMobileId\":10000,\"latitude\":-3.752630,\"longitude\":-38.513635,\"datePing\":1599905220000},{\"equipmentMobileId\":10000,\"latitude\":-3.752638,\"longitude\":-38.513635,\"datePing\":1599905280000},{\"equipmentMobileId\":10000,\"latitude\":-3.752635,\"longitude\":-38.513635,\"datePing\":1599905340000},{\"equipmentMobileId\":10000,\"latitude\":-3.752634,\"longitude\":-38.513635,\"datePing\":1599905400000},{\"equipmentMobileId\":10000,\"latitude\":-3.752698,\"longitude\":-38.513927,\"datePing\":1599905520000},{\"equipmentMobileId\":10000,\"latitude\":-3.7526670,\"longitude\":-38.5149107,\"datePing\":1599905580000},{\"equipmentMobileId\":10000,\"latitude\":-3.7522728,\"longitude\":-38.5162625,\"datePing\":1599905640000},{\"equipmentMobileId\":10000,\"latitude\":-3.751891,\"longitude\":-38.515098,\"datePing\":1599905700000},{\"equipmentMobileId\":10000,\"latitude\":-3.750795,\"longitude\":-38.515302,\"datePing\":1599905760000},{\"equipmentMobileId\":10000,\"latitude\":-3.749765,\"longitude\":-38.515491,\"datePing\":1599905820000},{\"equipmentMobileId\":10000,\"latitude\":-3.7500411,\"longitude\":-38.5161274,\"datePing\":1599905880000},{\"equipmentMobileId\":10000,\"latitude\":-3.7500410,\"longitude\":-38.5161274,\"datePing\":1599905940000},{\"equipmentMobileId\":10000,\"latitude\":-3.7500410,\"longitude\":-38.5161274,\"datePing\":1599906000000},{\"equipmentMobileId\":10000,\"latitude\":-3.7500410,\"longitude\":-38.5161274,\"datePing\":1599906060000},{\"equipmentMobileId\":10000,\"latitude\":-3.7500417,\"longitude\":-38.5161274,\"datePing\":1599906120000},{\"equipmentMobileId\":10000,\"latitude\":-3.7500416,\"longitude\":-38.5161274,\"datePing\":1599906180000},{\"equipmentMobileId\":10000,\"latitude\":-3.748937,\"longitude\":-38.515718,\"datePing\":1599906240000},{\"equipmentMobileId\":10000,\"latitude\":-3.748678,\"longitude\":-38.516117,\"datePing\":1599906300000},{\"equipmentMobileId\":10000,\"latitude\":-3.748645,\"longitude\":-38.516856,\"datePing\":1599906360000},{\"equipmentMobileId\":10000,\"latitude\":-3.74971,\"longitude\":-38.516425,\"datePing\":1599906480000},{\"equipmentMobileId\":10000,\"latitude\":-3.74977,\"longitude\":-38.516425,\"datePing\":1599906540000},{\"equipmentMobileId\":10000,\"latitude\":-3.74979,\"longitude\":-38.516425,\"datePing\":1599906600000},{\"equipmentMobileId\":10000,\"latitude\":-3.74975,\"longitude\":-38.516425,\"datePing\":1599906660000},{\"equipmentMobileId\":10000,\"latitude\":-3.74973,\"longitude\":-38.516425,\"datePing\":1599906720000},{\"equipmentMobileId\":10000,\"latitude\":-3.7506310,\"longitude\":-38.5178648,\"datePing\":1599906960000},{\"equipmentMobileId\":10000,\"latitude\":-3.7506310,\"longitude\":-38.5178640,\"datePing\":1599907020000},{\"equipmentMobileId\":10000,\"latitude\":-3.7506310,\"longitude\":-38.5178648,\"datePing\":1599907080000},{\"equipmentMobileId\":10000,\"latitude\":-3.7506310,\"longitude\":-38.5178648,\"datePing\":1599907140000}]"

        Flux.fromIterable(mapper.readValue(jsonContentCoordinate, Array<Coordinate>::class.java).asList())
            .delayElements(Duration.ofMillis(500))
            .flatMap { coordinate -> receivedCoordinate(coordinate) }
            .map { notificationDto-> eventDanger.processCoordinate(notificationDto) }
            .subscribe()

        Flux.fromIterable(mapper.readValue(jsonContentCoordinateMobile, Array<CoordinateMobile>::class.java).asList())


    }
}