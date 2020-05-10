package com.bugtsa.casher.resource.api.data.res

import org.codehaus.jackson.annotate.JsonProperty

data class PaymentPageRes(
        @field:JsonProperty("hasWarning")
        val hasWarning: Boolean,

        @field:JsonProperty("warning")
        val warningsList: List<PaymentPageWarningsRes>,

        @field:JsonProperty("page")
        val page: List<PaymentByDayRes>
)