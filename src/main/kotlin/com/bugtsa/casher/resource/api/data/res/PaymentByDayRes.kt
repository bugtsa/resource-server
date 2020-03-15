package com.bugtsa.casher.resource.api.data.res

import com.bugtsa.casher.resource.api.models.PaymentDto
import com.fasterxml.jackson.annotation.JsonProperty

data class PaymentByDayRes(

        @field:JsonProperty("id_payment")
        val id: String,

        @field:JsonProperty("date_payment")
        val date: String?,

        @field:JsonProperty("payment")
        val payment: PaymentDto?
)