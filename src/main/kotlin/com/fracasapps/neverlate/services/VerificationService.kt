package com.fracasapps.neverlate.services

import com.fracasapps.neverlate.models.PurchaseData
import com.fracasapps.neverlate.models.VerificationDetails
import com.fracasapps.neverlate.repositories.PurchasesRepository
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.services.androidpublisher.AndroidPublisher
import com.google.api.services.androidpublisher.model.SubscriptionPurchase
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VerificationService {

    @Autowired
    private lateinit var androidPublisher: AndroidPublisher

    @Autowired
    private lateinit var purchasesRepository: PurchasesRepository

    private var logger = LoggerFactory.getLogger(VerificationService::class.java)

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

    fun verifyPurchaseList(verificationDetailList: List<VerificationDetails>) : Boolean{
        print(verificationDetailList)
        verificationDetailList.forEach{
            if(databaseHasValidToken(it.token)) return true
            try{
                val purchaseInfo = androidPublisher.Purchases().subscriptions().get(it.packageName, it.sku, it.token).execute()
                System.err.println(purchaseInfo)
                if(purchaseInfo.expiryTimeMillis > System.currentTimeMillis() && purchaseInfo.cancelReason == null){
                    addPurchaseToDb(it.token, purchaseInfo)
                    return true
                }
            }catch (ex: GoogleJsonResponseException){
                return@forEach
            }
        }
        return false
    }

    private fun databaseHasValidToken(token: String): Boolean {
        purchasesRepository.findByToken(token)?.let { return System.currentTimeMillis() < it.expiryTime }
                ?: return false
    }

    fun getProducts(): String {
        val products = androidPublisher.inappproducts().list("com.aaronbrecher.neverlate").execute()
        return products.toPrettyString()
    }

    private fun addPurchaseToDb(token: String, purchaseInfo: SubscriptionPurchase) {
        purchasesRepository.save(PurchaseData(token = token,
                expiryTime = purchaseInfo.expiryTimeMillis,
                paymentState = purchaseInfo.paymentState,
                startTime = purchaseInfo.startTimeMillis,
                email = purchaseInfo.emailAddress))
    }
}