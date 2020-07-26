package com.bugtsa.casher.resource.auth.config

class DailyValidityAccessToken {

    val validity: Int

    init {
        validity = processMockValidity()
    }

    private fun processValidity() = SECONDS_IN_MINUTES * MINUTES_IN_HOUR * HOURS_IN_DAY

    private fun processMockValidity(): Int = 10

    companion object {
        private const val SECONDS_IN_MINUTES = 60
        private const val MINUTES_IN_HOUR = 60
        private const val HOURS_IN_DAY = 24
    }
}