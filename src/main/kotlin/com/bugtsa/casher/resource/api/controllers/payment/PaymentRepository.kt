package com.bugtsa.casher.resource.api.controllers.payment

import com.bugtsa.casher.resource.api.data.entity.Payment
import org.springframework.data.repository.PagingAndSortingRepository

interface PaymentRepository : PagingAndSortingRepository<Payment, Int> {
    fun findById(id: Long?): List<Payment>
}