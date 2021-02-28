package com.bugtsa.casher.resource.api.controllers.avangard.data

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

        val dateTimeExec: List<DateTimeExecUIModel>,

        val status: Int
)
