package com.bugtsa.casher.casherresourceserver.mainly.data.res

import com.bugtsa.casher.casherresourceserver.mainly.model.PaymentDto
import com.fasterxml.jackson.annotation.JsonProperty

data class PaymentByDayRes(

        @field:JsonProperty("id_payment")
        val id: String,

        @field:JsonProperty("date_payment")
        val date: String?,

        @field:JsonProperty("payment")
        val payment: PaymentDto?
)