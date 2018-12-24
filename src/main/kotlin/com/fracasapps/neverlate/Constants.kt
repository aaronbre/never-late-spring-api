package com.fracasapps.neverlate

//env keys
const val HERE_APP_ID = "HERE_APP_ID"
const val HERE_APP_CODE = "HERE_APP_CODE"

//here constants
const val HERE_APP_ID_QUERY_NAME = "app_id"
const val HERE_APP_CODE_QUERY_NAME = "app_code"
const val HERE_MODE_QUERY_NAME = "mode"

//here matrix constants
const val HERE_MATRIX_ENDPOINT = "https://matrix.route.api.here.com/routing/7.2/calculatematrix.json"
const val HERE_MATRIX_MODE_QUERY_VALUE = "fastest;car;traffic:enabled;"
const val HERE_MATRIX_SUMMARY_QUERY_NAME = "summaryAttributes"
const val HERE_MATRIX_SUMMARY_QUERY_VALUE = "tt,di"
const val HERE_MATRIX_ORIGIN_QUERY_NAME = "start0"

//here directions endpoints
const val HERE_DIRECTIONS_ENDPOINT = "https://route.api.here.com/routing/7.2/calculateroute.json"
const val HERE_DIRECTIONS_PUBLIC_MODE_QUERY_VALUE = "fastest;publicTransportTimeTable"
const val HERE_DIRECTIONS_DRIVING_MODE_QUERY_VALUE = "fastest;car;traffic:enabled;"
const val HERE_DIRECTIONS_ALLOW_CHANGE_QUERY_NAME = "combineChange"
const val HERE_DIRECTIONS_ALLOW_CHANGE_QUERY_VALUE = "true"
const val HERE_DIRECTIONS_ORIGIN_QUERY_NAME = "waypoint0"
const val HERE_DIRECTIONS_DESTINATION_QUERY_NAME = "waypoint1"
const val HERE_DIRECTIONS_DEPARTURE_QUERY_NAME = "departure"
const val HERE_DIRECTIONS_ARRIVAL_QUERY_NAME = "arrival"