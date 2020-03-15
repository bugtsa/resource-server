package com.bugtsa.casher.resource.api.data.res

import com.fasterxml.jackson.annotation.JsonProperty

data class PaymentRes(

        @field:JsonProperty("user_id")
        val userId: String,

        @field:JsonProperty("cost")
        val cost: String,

        @field:JsonProperty("amount")
        val amount: String,

        @field:JsonProperty("price")
        val price: String,

        @field:JsonProperty("date")
        val date: String,

        @field:JsonProperty("category")
        val category: String,

        @field:JsonProperty("category_id")
        val categoryId: Int,

        @field:JsonProperty("id")
        val id: Int,

        @field:JsonProperty("description")
        val description: String,

        @field:JsonProperty("balance")
        val balance: String
)