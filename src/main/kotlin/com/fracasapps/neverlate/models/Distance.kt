package com.fracasapps.neverlate.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Distance(var distance: Int = -1,
                    var duration: Int = -1)