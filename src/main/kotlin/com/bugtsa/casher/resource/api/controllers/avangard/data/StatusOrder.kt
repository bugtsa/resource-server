package com.bugtsa.casher.resource.api.controllers.avangard.data

sealed class StatusOrder(
        val status: Int
) {

    object Seen: StatusOrder(0)
}