package com.bugtsa.casher.casherresourceserver.mainly.model

import com.bugtsa.casher.casherresourceserver.mainly.data.entity.Payment
import com.bugtsa.casher.casherresourceserver.mainly.data.res.PaymentRes

class PaymentDto {

    val userId: String
    val cost: String
    val date: String
    val time: String
    val category: String
    var categoryId: Int
    var balance: String

    constructor(userId: String,
                cost: String,
                category: String,
                date: String) {
        this.userId = userId
        this.cost = cost
        this.date = date
        this.time = ""
        this.category = category
        this.categoryId = 0
        this.balance = ""
    }

    constructor(paymentRes: PaymentRes) {
        userId = paymentRes.userId
        cost = paymentRes.cost
        date = paymentRes.date
        time = ""
        category = paymentRes.category
        categoryId = paymentRes.categoryId
        balance = ""
    }

    constructor(payment: Payment) {
        userId = payment.userId
        cost = payment.cost
        date = payment.date
        time = ""
        category = payment.category
        categoryId = payment.categoryId
        balance = payment.balance
    }

    constructor(oldPayment: Payment,
                newDate: String,
                newTime: String) {
        this.userId = oldPayment.userId
        this.cost = oldPayment.cost
        this.date = newDate
        this.time = newTime
        this.category = oldPayment.category
        this.categoryId = oldPayment.categoryId
        this.balance = oldPayment.balance
    }
}