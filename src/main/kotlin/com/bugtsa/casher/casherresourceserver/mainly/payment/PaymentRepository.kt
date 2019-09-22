package com.bugtsa.casher.casherresourceserver.mainly.payment

import com.bugtsa.casher.casherresourceserver.mainly.data.entity.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository : PagingAndSortingRepository<Payment, Int>, JpaRepository<Payment, Int> {
    fun findById(id: Long?): List<Payment>
//    fun findByAuthor(author: String): List<Payment>
}