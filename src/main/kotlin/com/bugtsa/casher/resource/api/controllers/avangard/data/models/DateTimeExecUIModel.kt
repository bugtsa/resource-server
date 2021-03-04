package com.bugtsa.casher.resource.api.controllers.avangard.data.models

import kotlinx.serialization.Serializable

@Serializable
data class DateTimeExecUIModel(
        val dateExec: String,

        val timeStartExec: String,

        val timeEndExec: String
)