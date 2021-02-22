package com.bugtsa.casher.resource.api.controllers.avangard.data

sealed class StatusOrder(
        val status: Long
) {

    object Seen : StatusOrder(SEEN_VALUE)

    object Fail : StatusOrder(FAIL_VALUE)

    object First : StatusOrder(FIRST_ORDER_ID_VALUE)

    object Second : StatusOrder(SECOND_ORDER_ID_VALUE)

    object Third : StatusOrder(THIRD_ORDER_ID_VALUE)

    companion object {

        const val FIRST_ORDER_ID_VALUE = 5678912354
        const val SECOND_ORDER_ID_VALUE = 5678912355
        const val THIRD_ORDER_ID_VALUE = 5678912356

        private const val SEEN_VALUE = 0L
        private const val FAIL_VALUE = -1L

        fun Long.toStatusOrder(): StatusOrder =
                when (this) {
                    SEEN_VALUE -> Seen
                    FIRST_ORDER_ID_VALUE -> First
                    SECOND_ORDER_ID_VALUE -> Second
                    THIRD_ORDER_ID_VALUE -> Third
                    else -> Fail
                }

        fun StatusOrder.toId(): Long =
                when (this) {
                    Seen -> SEEN_VALUE
                    First -> FIRST_ORDER_ID_VALUE
                    Second -> SECOND_ORDER_ID_VALUE
                    Third -> THIRD_ORDER_ID_VALUE
                    Fail -> FAIL_VALUE
                }
    }
}