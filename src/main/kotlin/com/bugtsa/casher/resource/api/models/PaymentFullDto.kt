package com.bugtsa.casher.resource.api.models

import com.bugtsa.casher.resource.api.data.res.PaymentRes

class PaymentFullDto {

    val userId: String
    val cost: String
    val date: String
    val category: String
    var categoryId: Int
    val balance: String
    val amount: String
    val price: String
    val description: String

    constructor(userId: String,
                cost: String,
                category: String,
                date: String,
                balance: String,
                amount: String,
                price: String,
                description: String) {
        this.userId = userId
        this.cost = cost
        this.date = date
        this.category = category
        this.categoryId = 0
        this.balance = balance
        this.price = price
        this.amount = amount
        this.description = description
    }

    constructor(paymentRes: PaymentRes) {
        userId = paymentRes.userId
        cost = paymentRes.cost
        date = paymentRes.date
        category = paymentRes.category
        categoryId = paymentRes.categoryId
        balance = paymentRes.balance
        amount = paymentRes.amount
        price = paymentRes.price
        description = paymentRes.description
    }
}