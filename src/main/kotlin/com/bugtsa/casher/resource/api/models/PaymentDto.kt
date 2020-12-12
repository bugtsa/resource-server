package com.bugtsa.casher.resource.api.models

import com.bugtsa.casher.resource.api.data.entity.Payment
import com.bugtsa.casher.resource.api.data.res.PaymentRes

class PaymentDto {

    val id: Int
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
        this.id = INT_DEFAULT_VALUE
        this.userId = userId
        this.cost = cost
        this.date = date
        this.time = STRING_DEFAULT_VALUE
        this.category = category
        this.categoryId = INT_DEFAULT_VALUE
        this.balance = STRING_DEFAULT_VALUE
    }

    constructor(paymentRes: PaymentRes) {
        id = paymentRes.id
        userId = paymentRes.userId
        cost = paymentRes.cost
        date = paymentRes.date
        time = STRING_DEFAULT_VALUE
        category = paymentRes.category
        categoryId = paymentRes.categoryId
        balance = STRING_DEFAULT_VALUE
    }

    constructor(payment: Payment) {
        id = payment.id
        userId = payment.userId
        cost = payment.cost
        date = payment.date
        time = STRING_DEFAULT_VALUE
        category = payment.category
        categoryId = payment.categoryId
        balance = payment.balance
    }

    constructor(oldPayment: Payment,
                newDate: String,
                newTime: String) {
        this.id = oldPayment.id
        this.userId = oldPayment.userId
        this.cost = oldPayment.cost
        this.date = newDate
        this.time = newTime
        this.category = oldPayment.category
        this.categoryId = oldPayment.categoryId
        this.balance = oldPayment.balance
    }

    override fun toString(): String {
        return "id: $id \n" +
                "userId: $userId \n" +
                "cost: $cost \n" +
                "date: $date \n" +
                "time: $time \n" +
                "category: $category \n" +
                "balance: $balance"
    }

    companion object {
        private const val INT_DEFAULT_VALUE = 0
        private const val STRING_DEFAULT_VALUE = ""
    }
}