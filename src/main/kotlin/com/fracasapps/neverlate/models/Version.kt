package com.fracasapps.neverlate.models

data class Version(var version: Int = 0,
                   var message: String = "",
                   var needsUpdate: Boolean = false)