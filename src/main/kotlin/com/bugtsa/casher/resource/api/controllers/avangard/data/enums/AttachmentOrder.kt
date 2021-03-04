package com.bugtsa.casher.resource.api.controllers.avangard.data.enums

sealed class AttachmentOrder {

    object First : AttachmentOrder()

    object Second : AttachmentOrder()

    object Third : AttachmentOrder()

    object Fail : AttachmentOrder()

    companion object {

        fun Long.toAttachment(): AttachmentOrder =
                when(this) {
                    OrderPageSet.FIRST_ORDER_ID_VALUE -> First
                    OrderPageSet.SECOND_ORDER_ID_VALUE -> Second
                    OrderPageSet.THIRD_ORDER_ID_VALUE -> Third
                    else -> Fail
                }
    }
}