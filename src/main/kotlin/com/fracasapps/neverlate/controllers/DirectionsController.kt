package com.fracasapps.neverlate.controllers

import com.fracasapps.neverlate.models.Distance
import com.fracasapps.neverlate.models.EventDetails
import com.fracasapps.neverlate.models.Version
import com.fracasapps.neverlate.services.DirectionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class DirectionsController {

    @Autowired
    private lateinit var directionsService: DirectionsService

    @GetMapping("/")
    fun homeMessage(): String {
        return "This is the home of neverlate app api"
    }

    @GetMapping("/current-version")
    fun checkVersion(): Version{
        return Version(version = 33, needsUpdate = false)
    }

    @PostMapping("/direction-matrix")
    fun directionMatrix(@RequestParam("origin") origin: String,
                        @RequestBody destinations: List<EventDetails>): List<Distance> {
        return directionsService.queryHereMatrix(origin, destinations)
        //TODO add code to check for subscription validation and if none throw following exception
        //throw ForbiddenException()
    }

    @PostMapping("/directions")
    fun directions(@RequestParam("origin") origin: String,
                   @RequestBody destination: EventDetails): Distance {
        return directionsService.queryDirections(origin, destination, false)
    }

    @PostMapping("/public-transit")
    fun herePublicTransport(@RequestParam("origin") origin: String,
                            @RequestBody destinations: List<EventDetails>): List<Distance> {
        return directionsService.getPublicTransportDirections(origin, destinations)
    }


}

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Subscription Invalid")
class ForbiddenException : RuntimeException()
