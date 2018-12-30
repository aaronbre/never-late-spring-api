package com.fracasapps.neverlate.models

data class DirectionsRequestBody(var destinations: List<EventDetails> = ArrayList(),
                        var purchases: List<VerificationDetails> = ArrayList())

data class VerificationDetails(var token: String = "",
                                var sku: String = "",
                                var packageName: String = "")

data class EventDetails(var longitude: Double = 0.0,
                        var latitude: Double = 0.0,
                        var time: String = "")