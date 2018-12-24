package com.fracasapps.neverlate.controllers

import com.fracasapps.neverlate.services.VerificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class VerificationController {

    @Autowired
    private lateinit var verificationService: VerificationService

    @GetMapping("/verify-purchase")
    fun verifyPurchase(@RequestParam("token") token: String,
                       @RequestParam("productId") productId: String,
                       @RequestParam("packageName") packageName: String): Boolean {
        return verificationService.verifyPurchase(token,productId,packageName)
    }

    @GetMapping("/products")
    fun getProducts(): String{
        verificationService.getProducts()
        return ""
    }
}