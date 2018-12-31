package com.fracasapps.neverlate.controllers

import com.fracasapps.neverlate.models.DirectionsRequestBody
import com.fracasapps.neverlate.models.Distance
import com.fracasapps.neverlate.models.Version
import com.fracasapps.neverlate.services.DirectionsService
import com.fracasapps.neverlate.services.VerificationService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class DirectionsController {

    @Autowired
    private lateinit var directionsService: DirectionsService
    @Autowired
    private lateinit var verificationService: VerificationService

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
                        @RequestBody requestBody: DirectionsRequestBody): List<Distance> {
        System.err.println(requestBody.toString())
        if(verificationService.verifyPurchaseList(requestBody.purchases)){
            return directionsService.queryHereMatrix(origin, requestBody.locationDetails)
        }
        else throw ForbiddenException()
    }

    @PostMapping("/directions")
    fun directions(@RequestParam("origin") origin: String,
                   @RequestBody requestBody: DirectionsRequestBody): Distance {
        if(verificationService.verifyPurchaseList(requestBody.purchases)){
            return directionsService.queryDirections(origin, requestBody.locationDetails.first(), false)
        }
        else throw ForbiddenException()
    }

    @PostMapping("/public-transit")
    fun herePublicTransport(@RequestParam("origin") origin: String,
                            @RequestBody requestBody: DirectionsRequestBody): List<Distance> {
        if(verificationService.verifyPurchaseList(requestBody.purchases)) {
            return directionsService.getPublicTransportDirections(origin, requestBody.locationDetails)
        }
        else throw ForbiddenException()
    }


}

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Subscription Invalid")
class ForbiddenException : RuntimeException()
