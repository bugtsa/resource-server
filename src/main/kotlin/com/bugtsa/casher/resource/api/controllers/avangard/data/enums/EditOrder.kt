package com.bugtsa.casher.resource.api.controllers.avangard.data.enums

import com.bugtsa.casher.resource.api.controllers.avangard.data.models.OrderFullUIModel

sealed class EditOrder {

    object Success : EditOrder()

    object Fail : EditOrder()

    companion object {

        fun Long.toEditOrder(orderFull: OrderFullUIModel): EditOrder =
                when (this) {
                    OrderPageSet.FIRST_ORDER_ID_VALUE,
                    OrderPageSet.SECOND_ORDER_ID_VALUE,
                    OrderPageSet.THIRD_ORDER_ID_VALUE ->  {
                        if (orderFull.equipment == "Luck") {
                            Success
                        } else {
                            Fail
                        }
                    }
                    else -> Fail
                }
    }
}