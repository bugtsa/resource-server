package com.bugtsa.casher.resource.api.controllers.avangard.data.enums

sealed class OrderSet {

    object Fail : OrderSet()

    object First : OrderSet()

    object Second : OrderSet()

    object Third : OrderSet()

    companion object {

        fun Long.toOrderStatus(): OrderSet =
                when (this) {
                    OrderPageSet.FIRST_ORDER_ID_VALUE -> First
                    OrderPageSet.SECOND_ORDER_ID_VALUE -> Second
                    OrderPageSet.THIRD_ORDER_ID_VALUE -> Third
                    else -> Fail
                }
    }
}