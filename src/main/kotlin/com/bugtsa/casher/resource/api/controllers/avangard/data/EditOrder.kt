package com.bugtsa.casher.resource.api.controllers.avangard.data

sealed class EditOrder {

    object Success : EditOrder()

    object Fail : EditOrder()

    companion object {

        fun Long.toEditOrder(equipment: String): EditOrder =
                when (this) {
                    StatusOrder.FIRST_ORDER_ID_VALUE,
                    StatusOrder.SECOND_ORDER_ID_VALUE,
                    StatusOrder.THIRD_ORDER_ID_VALUE ->  {
                        if (equipment == "Luck") {
                            Success
                        } else {
                            Fail
                        }
                    }
                    else -> Fail
                }
    }
}