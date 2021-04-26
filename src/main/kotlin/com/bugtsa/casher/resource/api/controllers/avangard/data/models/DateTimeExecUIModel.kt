package com.bugtsa.casher.resource.api.controllers.avangard.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class DateTimeExecUIModel(
        @field:JsonProperty("date_exec")
        val dateExec: String,
        @field:JsonProperty("time_start_exec")
        val timeStartExec: String,
        @field:JsonProperty("time_end_exec")
        val timeEndExec: String
)