package com.fracasapps.neverlate.repositories

import com.fracasapps.neverlate.models.PurchaseData
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PurchasesRepository: CrudRepository<PurchaseData, Long>

