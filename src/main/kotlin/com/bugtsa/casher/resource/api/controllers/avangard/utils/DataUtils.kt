package com.bugtsa.casher.resource.api.controllers.avangard.utils

import java.text.SimpleDateFormat
import java.util.*

object DataUtils {

    private const val DEFAULT_LOCALE = "ru"

    private const val FORMAT_SIMPLE_TIME = "HH:mm:ss"

    fun getHoursAndMinutes(dateString: String): Pair<Int, Int> {
        val orderDate = Calendar.getInstance()
        orderDate.time = getDateFromString(dateString)

        val (hours, minutes) = orderDate[Calendar.HOUR] to orderDate[Calendar.MINUTE]
        return hours to minutes
    }


    private fun getDateFromString(stringDate: String?): Date = runCatching {
        SimpleDateFormat(
                FORMAT_SIMPLE_TIME,
                Locale.forLanguageTag(DEFAULT_LOCALE)
        ).parse(stringDate ?: "")
    }.getOrElse {
        Date()
    }


}