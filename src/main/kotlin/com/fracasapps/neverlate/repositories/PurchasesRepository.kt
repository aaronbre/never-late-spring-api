package com.fracasapps.neverlate.repositories

import com.fracasapps.neverlate.models.PurchaseData
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PurchasesRepository: CrudRepository<PurchaseData, Long>{
    fun findByToken(token: String) : PurchaseData?

}

fun <T, ID> CrudRepository<T, ID>.findOne(id: ID): T? = findById(id).orElse(null)