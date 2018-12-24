package com.fracasapps.neverlate.services

import com.fracasapps.neverlate.models.PurchaseData
import com.fracasapps.neverlate.repositories.PurchasesRepository
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.services.androidpublisher.AndroidPublisher
import com.google.api.services.androidpublisher.model.SubscriptionPurchase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VerificationService {

    @Autowired
    private lateinit var androidPublisher: AndroidPublisher

    @Autowired
    private lateinit var purchasesRepository: PurchasesRepository


    fun verifyPurchase(purchaseToken: String, productId: String, packageName: String): Boolean {
        if (databaseHasValidToken(purchaseToken)) return true
        var valid = false
        return try {
            val purchaseInfo = androidPublisher.Purchases().subscriptions().get(packageName, productId, purchaseToken).execute()
            if(purchaseInfo.expiryTimeMillis > System.currentTimeMillis()){
                addPurchaseToDb(purchaseToken, purchaseInfo)
                valid = true
            }
            valid
        } catch (ex: GoogleJsonResponseException) {
            false
        }
    }

    private fun databaseHasValidToken(token: String): Boolean {
        purchasesRepository.findByToken(token)?.let { return System.currentTimeMillis() < it.expiryTime }
                ?: return false
    }

    fun getProducts(): String {
        val products = androidPublisher.inappproducts().list("com.aaronbrecher.neverlate").execute()
        purchasesRepository.save(PurchaseData(token = "1kdahdu2", paymentState = 2, expiryTime = 42, startTime = 24, autoResumeTime = 52))
        return products.toPrettyString()
    }

    private fun addPurchaseToDb(token: String, purchaseInfo: SubscriptionPurchase) {
        purchasesRepository.save(PurchaseData(token = token,
                expiryTime = purchaseInfo.expiryTimeMillis,
                paymentState = purchaseInfo.paymentState,
                startTime = purchaseInfo.startTimeMillis,
                autoResumeTime = purchaseInfo.autoResumeTimeMillis,
                email = purchaseInfo.emailAddress))
    }
}