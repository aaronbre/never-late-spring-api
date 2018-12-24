package com.fracasapps.neverlate.models.hereapimodels

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class HereMatrixResponse(
        var response: MatrixResponse = MatrixResponse()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatrixResponse(
        var matrixEntry: List<MatrixEntry> = ArrayList(),
        var metaInfo: MatrixMetaInfo = MatrixMetaInfo()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatrixMetaInfo(
        var availableMapVersion: List<String> = ArrayList(),
        var interfaceVersion: String = "",
        var mapVersion: String = "",
        var moduleVersion: String = "",
        var timestamp: String = ""
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatrixEntry(
        var destinationIndex: Int = 0,
        var startIndex: Int = 0,
        var summary: MatrixSummary = MatrixSummary()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatrixSummary(
        var costFactor: Int = 0,
        var distance: Int = 0,
        var travelTime: Int = 0
)