package com.bugtsa.casher.resource.api.controllers.avangard.data.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class OrderFullUIModel(

        val id: Long,

        val nameTech: Int,

        val dateTimeCreate: String,

        val num: String,

        val latitude: String,

        val equipment: String,

        val typeTech: Int,

        val addressName: String,

        val defect: String,

        val stage: String,

        val tel: String,

        val longitude: String,

        @SerialName("date_time_exec")
        val dateTimeExec: List<DateTimeExecUIModel>,

        val status: Int
)
