package com.bugtsa.casher.resource.api.controllers.avangard.data.enums

sealed class StatusOrderType(
        val statusOrder: Int
) {

    object Open : StatusOrderType(OPEN_ORDER)

    object InWork : StatusOrderType(IN_WORK_ORDER)

    object Waiting : StatusOrderType(WAITING_ORDER)

    object Ready : StatusOrderType(READY_ORDER)

    object Complete : StatusOrderType(COMPLETE_ORDER)

    object Fail : StatusOrderType(-1)

    companion object {

        const val OPEN_ORDER = 0
        const val IN_WORK_ORDER = 1
        const val WAITING_ORDER = 2
        const val READY_ORDER = 3
        const val COMPLETE_ORDER = 4

        fun Int.toStatusOrderType(): StatusOrderType =
                when(this) {
                    OPEN_ORDER -> Open
                    IN_WORK_ORDER -> InWork
                    WAITING_ORDER -> Waiting
                    READY_ORDER -> Ready
                    COMPLETE_ORDER -> Complete
                    else -> Fail
                }
    }
}