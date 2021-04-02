package com.bugtsa.casher.resource.api.controllers.avangard.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderNotificationUIModel(
        @field:JsonProperty("uuid")
        val uuid: String,
        @field:JsonProperty("notification_timeorder")
        val time: String,
        @field:JsonProperty("comment")
        val comment: String
)