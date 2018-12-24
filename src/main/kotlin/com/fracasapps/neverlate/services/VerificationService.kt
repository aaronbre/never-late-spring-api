package com.fracasapps.neverlate.services

import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.services.androidpublisher.AndroidPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VerificationService{

    @Autowired
    private lateinit var androidPublisher: AndroidPublisher

//    @Autowired
//    private lateinit var purchasesRepository: PurchaseRepository


    fun verifyPurchase(purchaseToken: String, productId: String, packageName: String): Boolean{
        //first check DB if valid then return true

        //If not in DB then query the API to see if it exists
        return try {
            val purchaseInfo = androidPublisher.Purchases().subscriptions().get(packageName, productId, purchaseToken).execute()
            //add info to DB

            purchaseInfo == null
        } catch (ex: GoogleJsonResponseException){
            false
        }
    }

    fun getProducts(): String{
        val products = androidPublisher.inappproducts().list("com.aaronbrecher.neverlate").execute()
        //purchasesRepository.save(PurchaseData(token = "1kdahdu2", paymentState = 2, expiryTime = 42, startTime = 24, autoResumeTime = 52))
        return products.toPrettyString()
    }
}