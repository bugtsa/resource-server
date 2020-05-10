package com.bugtsa.casher.resource.api.data.res

import com.bugtsa.casher.resource.api.models.PaymentDto
import org.codehaus.jackson.annotate.JsonProperty

data class PaymentPageWarningsRes(
        @field:JsonProperty("title")
        val title: String,

        @field:JsonProperty("warnings")
        val warning: PaymentDto)