package com.fracasapps.neverlate.models.hereapimodels

data class HereDirectionsResponse(
        var response: DirectionsResponse = DirectionsResponse()
)

data class DirectionsResponse(
        var language: String = "",
        var metaInfo: DirectionsMetaInfo = DirectionsMetaInfo(),
        var route: List<Route> = ArrayList()
)

data class DirectionsMetaInfo(
        var availableMapVersion: List<String> = ArrayList(),
        var interfaceVersion: String = "",
        var mapVersion: String = "",
        var moduleVersion: String = "",
        var timestamp: String = ""
)

data class Route(
        var mode: Mode = Mode(),
        var summary: DirectionsSummary = DirectionsSummary(),
        var waypoint: List<Waypoint> = ArrayList()
)

data class DirectionsSummary(
        var _type: String = "",
        var baseTime: Int = -1,
        var departure: String = "",
        var distance: Int = -1,
        var flags: List<String> = ArrayList(),
        var text: String = "",
        var timetableExpiration: String = "",
        var travelTime: Int = -1
)

data class Mode(
        var feature: List<Any> = ArrayList(),
        var trafficMode: String = "",
        var transportModes: List<String> = ArrayList(),
        var type: String = ""
)

data class Waypoint(
        var label: String = "",
        var linkId: String = "",
        var mappedPosition: Position = Position(),
        var mappedRoadName: String = "",
        var originalPosition: Position = Position(),
        var shapeIndex: Int = 0,
        var sideOfStreet: String = "",
        var spot: Int = 0,
        var type: String = ""
)

data class Position(
        var latitude: Double = 0.0,
        var longitude: Double = 0.0
)