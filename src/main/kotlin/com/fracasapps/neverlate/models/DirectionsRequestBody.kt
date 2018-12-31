package com.fracasapps.neverlate.models

data class DirectionsRequestBody(var locationDetails: List<EventDetails> = ArrayList(),
                        var purchases: List<VerificationDetails> = ArrayList())

data class VerificationDetails(var token: String = "",
                                var sku: String = "",
                                var packageName: String = "")

data class EventDetails(var latitude: String = "",
                        var longitude: String = "",
                        var time: String = "")