package com.bugtsa.casher.resource.api.controllers.avangard.data.enums

sealed class OrderPageSet(
        val orderId: Long
) {

    object Fail : OrderPageSet(FAIL_VALUE)

    object First : OrderPageSet(FIRST_ORDER_ID_VALUE)

    object Second : OrderPageSet(SECOND_ORDER_ID_VALUE)

    object Third : OrderPageSet(THIRD_ORDER_ID_VALUE)

    companion object {

        const val FIRST_ORDER_ID_VALUE = 5678912354
        const val SECOND_ORDER_ID_VALUE = 5678912355
        const val THIRD_ORDER_ID_VALUE = 5678912356

        private const val SEEN_VALUE = 0L
        private const val FAIL_VALUE = -1L

        fun Long.toOrder(): OrderPageSet =
                when (this) {
                    FIRST_ORDER_ID_VALUE -> First
                    SECOND_ORDER_ID_VALUE -> Second
                    THIRD_ORDER_ID_VALUE -> Third
                    else -> Fail
                }

        fun OrderPageSet.toId(): Long =
                when (this) {
                    First -> FIRST_ORDER_ID_VALUE
                    Second -> SECOND_ORDER_ID_VALUE
                    Third -> THIRD_ORDER_ID_VALUE
                    Fail -> FAIL_VALUE
                }
    }
}