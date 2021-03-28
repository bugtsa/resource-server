package com.bugtsa.casher.resource.api.controllers.avangard.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class NotificationUIModel(
        @field:JsonProperty("uuid")
        val uuid: String,
        @field:JsonProperty("notification_time")
        val time: String,
        @field:JsonProperty("notification_comment")
        val comment: String
)