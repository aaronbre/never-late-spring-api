package com.fracasapps.neverlate.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class PurchaseData(@Id
                        @GeneratedValue
                        var id: Long? = null,
                        var token: String = "",
                        var paymentState: Int = -1,
                        var expiryTime: Long = -1,
                        var startTime: Long = -1,
                        var autoResumeTime: Long = -1)