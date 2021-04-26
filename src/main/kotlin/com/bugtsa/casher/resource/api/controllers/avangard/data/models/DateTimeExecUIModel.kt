package com.bugtsa.casher.resource.api.controllers.avangard.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DateTimeExecUIModel(
        @SerialName("date_exec")
        val dateExec: String,
        @SerialName("time_start_exec")
        val timeStartExec: String,
        @SerialName("time_end_exec")
        val timeEndExec: String
)