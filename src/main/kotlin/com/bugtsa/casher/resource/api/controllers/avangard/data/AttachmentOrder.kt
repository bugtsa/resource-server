package com.bugtsa.casher.resource.api.controllers.avangard.data

sealed class AttachmentOrder {

    object First : AttachmentOrder()

    object Second : AttachmentOrder()

    object Third : AttachmentOrder()

    object Fail : AttachmentOrder()

    companion object {

        fun Long.toAttachment(): AttachmentOrder =
                when(this) {
                    StatusOrder.FIRST_ORDER_ID_VALUE -> First
                    StatusOrder.SECOND_ORDER_ID_VALUE -> Second
                    StatusOrder.THIRD_ORDER_ID_VALUE -> Third
                    else -> Fail
                }
    }
}