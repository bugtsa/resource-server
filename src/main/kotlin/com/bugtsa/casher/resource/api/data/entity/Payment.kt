package com.bugtsa.casher.resource.api.data.entity

import com.bugtsa.casher.resource.api.models.PaymentDto
import com.bugtsa.casher.resource.api.models.PaymentFullDto
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    val userId: String
    val cost: String
    val date: String
    val category: String
    val categoryId: Int
    val balance: String
    val price: String
    val amount: String
    val description: String

    constructor(payment: PaymentDto) {
        this.userId = payment.userId
        this.cost = payment.cost
        this.date = payment.date
        this.categoryId = payment.categoryId
        this.category = payment.category
        this.balance = payment.balance
        this.amount = ""
        this.price = ""
        this.description = ""
    }

    constructor(payment: PaymentFullDto) {
        this.userId = payment.userId
        this.cost = payment.cost
        this.date = payment.date
        this.categoryId = 0
        this.category = payment.category
        this.balance = payment.balance
        this.amount = payment.amount
        this.price = payment.price
        this.description = payment.description
    }
}