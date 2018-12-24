package com.fracasapps.neverlate.services

import com.fracasapps.neverlate.*
import com.fracasapps.neverlate.models.Distance
import com.fracasapps.neverlate.models.EventDetails
import com.fracasapps.neverlate.models.Secrets
import com.fracasapps.neverlate.models.hereapimodels.HereDirectionsResponse
import com.fracasapps.neverlate.models.hereapimodels.HereMatrixResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.ArrayList

private const val MAX_MATRIX_SIZE = 99

@Service
class DirectionsService {

    @Autowired
    private lateinit var secrets: Secrets

    @Autowired
    private lateinit var restTemplate: RestTemplate


    fun queryHereMatrix(origin: String, destinations: List<EventDetails>): List<Distance> {
        val response = ArrayList<Distance>()
        destinations.chunked(MAX_MATRIX_SIZE).forEach{
            val matrix = restTemplate.getForObject(createHereMatrixUrl(origin, it), HereMatrixResponse::class.java) ?: return response
            matrix.response.matrixEntry.mapTo(response, { Distance(it.summary.distance, it.summary.travelTime) })
        }
        return response
    }

    private fun createHereMatrixUrl(origin: String, destinations: List<EventDetails>): String {
        val builder = UriComponentsBuilder.fromHttpUrl(HERE_MATRIX_ENDPOINT)
                .queryParam(HERE_MODE_QUERY_NAME, HERE_MATRIX_MODE_QUERY_VALUE)
                .queryParam(HERE_APP_ID_QUERY_NAME, secrets.hereAppId)
                .queryParam(HERE_APP_CODE_QUERY_NAME, secrets.hereAppCode)
                .queryParam(HERE_MATRIX_ORIGIN_QUERY_NAME, origin)
                .queryParam(HERE_MATRIX_SUMMARY_QUERY_NAME, HERE_MATRIX_SUMMARY_QUERY_VALUE)
        addMatrixDestinations(destinations, builder)
        return builder.toUriString()
    }

    private fun addMatrixDestinations(destinations: List<EventDetails>, builder: UriComponentsBuilder) {
        (0 until destinations.size).forEach{
            val coords = destinations[it].latitude.toString() + "," + destinations[it].longitude
            builder.queryParam("destination" + it, coords)
        }
    }

    fun getPublicTransportDirections(origin: String, destinations: List<EventDetails>): List<Distance> {
        val response = ArrayList<Distance>()
        destinations.forEach{
            response.add(queryDirections(origin, it, true))
        }
        return response
    }

    fun queryDirections(origin: String, destination: EventDetails, publicTransport: Boolean = false): Distance {
        val destinationCoords = destination.latitude.toString() + "," + destination.longitude
        val url = if (publicTransport) createHerePublicTransportBaseUrl(origin, destinationCoords, destination.time)
        else createDrivingBaseUrl(origin, destinationCoords)

        val directions = restTemplate.getForObject(url, HereDirectionsResponse::class.java) ?: return Distance()
        val summary = directions.response.route[0].summary
        return Distance(summary.distance, summary.baseTime)
    }

    private fun createDrivingBaseUrl(origin: String, destination: String): String {
        return UriComponentsBuilder.fromHttpUrl(HERE_DIRECTIONS_ENDPOINT)
                .queryParam(HERE_MODE_QUERY_NAME, HERE_DIRECTIONS_DRIVING_MODE_QUERY_VALUE)
                .queryParam(HERE_DIRECTIONS_ORIGIN_QUERY_NAME, origin)
                .queryParam(HERE_DIRECTIONS_DESTINATION_QUERY_NAME, destination)
                .queryParam(HERE_DIRECTIONS_DEPARTURE_QUERY_NAME, "now")
                .queryParam(HERE_APP_CODE_QUERY_NAME, secrets.hereAppCode)
                .queryParam(HERE_APP_ID_QUERY_NAME, secrets.hereAppId)
                .toUriString()
    }

    fun createHerePublicTransportBaseUrl(origin: String, destination: String, arrivalTime: String = ""): String {
        val builder = UriComponentsBuilder.fromHttpUrl(HERE_DIRECTIONS_ENDPOINT)
                .queryParam(HERE_MODE_QUERY_NAME, HERE_DIRECTIONS_PUBLIC_MODE_QUERY_VALUE)
                .queryParam(HERE_DIRECTIONS_ALLOW_CHANGE_QUERY_NAME, HERE_DIRECTIONS_ALLOW_CHANGE_QUERY_VALUE)
                .queryParam(HERE_DIRECTIONS_ORIGIN_QUERY_NAME, origin)
                .queryParam(HERE_DIRECTIONS_DESTINATION_QUERY_NAME, destination)
                .queryParam(HERE_APP_CODE_QUERY_NAME, secrets.hereAppCode)
                .queryParam(HERE_APP_ID_QUERY_NAME, secrets.hereAppId)
        if (arrivalTime == "") {
            builder.queryParam(HERE_DIRECTIONS_DEPARTURE_QUERY_NAME, "now")
        } else {
            builder.queryParam(HERE_DIRECTIONS_ARRIVAL_QUERY_NAME, arrivalTime)
        }
        return builder.toUriString()
    }

}