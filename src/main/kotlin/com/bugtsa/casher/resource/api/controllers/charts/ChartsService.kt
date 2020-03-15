package com.bugtsa.casher.resource.api.controllers.charts

import com.bugtsa.casher.resource.api.controllers.payment.PaymentRepository
import com.bugtsa.casher.resource.api.controllers.payment.PaymentService
import com.bugtsa.casher.resource.api.data.entity.Payment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Service
class ChartsService {

    @Autowired
    private lateinit var paymentRepository: PaymentRepository

    fun getPaymentsByUserDateOutputInfo(userId: Int, requestMonth: Int,
                                        requestYear: Int, sortType: Int): CategorizedInfo {
        val paymentsList = hashMapOf<String, Float>()

        getPaymentsByUserDate(userId, requestMonth, transformToCorrectYear(requestYear)).forEach { payment ->
            val newValue = paymentsList[payment.category] ?: 0.0f
            paymentsList[payment.category] = payment.cost.toFloat() + newValue
        }
        val type = when (sortType) {
            WITHOUT_SORT_TYPE -> PaymentService.PaymentCategorizedSortType.WITHOUT
            ASC_SORT_TYPE -> PaymentService.PaymentCategorizedSortType.ASC
            DESC_SORT_TYPE -> PaymentService.PaymentCategorizedSortType.DESC
            else -> PaymentService.PaymentCategorizedSortType.WITHOUT
        }
        val list = paymentsList.toList()
        val sortedList = when (type) {
            PaymentService.PaymentCategorizedSortType.WITHOUT -> {
                list
            }
            PaymentService.PaymentCategorizedSortType.ASC -> {
                list.sortedBy { (_, value) -> value }
            }
            PaymentService.PaymentCategorizedSortType.DESC -> {
                list.sortedBy { (_, value) -> value }
                        .asReversed()
            }
        }
        return CategorizedInfo(requestYear.toString(),
                requestMonth.toString(),
                getCostAllPayments(paymentsList.values),
                sortedList
                        .map { (key, value) -> key to value.toString() }
                        .toMap())
    }

    fun getPaymentsByStartEndRange(userId: Int, startMonth: Int, startYear: Int, endMonth: Int, endYear: Int, sortType: Int): List<CategorizedInfo> {
        return if (startYear != endYear) {
            val list = mutableListOf<CategorizedInfo>()
            val dateRange = getDateRange(startYear, startMonth, endYear, endMonth)
            list.addAll(getListCategorizedInfo(userId, dateRange.startMonth, LAST_NUMBER_MONTH, dateRange.startYear, sortType))

            for (year in (dateRange.startYear + 1) until dateRange.endYear) {
                list.addAll(getListCategorizedInfo(userId, FIRST_NUMBER_MONTH, LAST_NUMBER_MONTH, year, sortType))
            }
            list.addAll(getListCategorizedInfo(userId, FIRST_NUMBER_MONTH, dateRange.endMonth, dateRange.endYear, sortType))
            list
        } else {
            if (startMonth != endMonth) {
                val minMonth = minOf(startMonth, endMonth)
                val maxMonth = maxOf(startMonth, endMonth)
                getListCategorizedInfo(userId, minMonth, maxMonth, startYear, sortType)
            } else {
                listOf(getPaymentsByUserDateOutputInfo(userId, startMonth, startYear, sortType))
            }
        }
    }

    private fun getDateRange(startYear: Int, startMonth: Int, endYear: Int, endMonth: Int): DateRange {
        var minYear = startYear
        var maxYear = endYear
        var minMonth = startMonth
        var maxMonth = endMonth
        if (startYear > endYear) {
            maxYear = startYear
            minYear = endYear
            maxMonth = startMonth
            minMonth = endMonth
        }
        return DateRange(minYear, minMonth, maxYear, maxMonth)
    }

    private fun getListCategorizedInfo(userId: Int, minMonth: Int, maxMonth: Int, year: Int, sortType: Int): List<CategorizedInfo> {
        val listCategorizedInfo = mutableListOf<CategorizedInfo>()
        for (month in minMonth..maxMonth) {
            listCategorizedInfo.add(getPaymentsByUserDateOutputInfo(userId, month, year, sortType))
        }
        return listCategorizedInfo
    }

    fun getPaymentsByUserDate(userId: Int, requestMonth: Int, requestYear: Int): MutableList<Payment> {
        val list = mutableListOf<Payment>()
        paymentRepository.findAll().forEach { payment ->
            val dateTimeFormat = payment.date.contains(DATE_AND_TIME_DELIMITER)

            val date = if (dateTimeFormat) {
                payment.date.subSequence(0, payment.date.indexOf(DATE_AND_TIME_DELIMITER))
            } else {
                payment.date
            }
            val dateSplit = date.split(".")
            if (dateSplit.isNotEmpty() && dateSplit.size >= 2) {
                try {
                    val monthNumber = dateSplit[MONTH_POSITION_IN_DATE].toInt()
                    val yearNumber = dateSplit[YEAR_POSITION_IN_DATE].toInt()
                    if (payment.userId.toInt() == userId && requestMonth == monthNumber && requestYear == yearNumber) {
                        list.add(payment)
                    }
                } catch (e: NumberFormatException) {
                }
            } else {
                if (payment.userId.toInt() == userId) {
                    list.add(payment)
                }
            }
        }
        return list
    }

    fun getRangeMonthAndYear(): List<Payment> {
        val sorted = paymentRepository.findAll()
                .sortedWith(Comparator { payment1, payment2 ->
                    try {
                        val date1 = processLocalDate(payment1.date)
                        val date2 = processLocalDate(payment2.date)
                        date1.compareTo(date2)
                    } catch (e: DateTimeParseException) {
                        throw IllegalArgumentException(e)
                    }
                })
        val list = mutableListOf<Payment>()
        list.add(sorted.first())
        list.add(sorted.last())
        return list
    }

    private fun processLocalDate(date: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(SHORT_DATE_FORMAT)
        val shortFormatter = DateTimeFormatter.ofPattern(LESS_TEN_DAYS_SHORT_DATE_FORMAT)
        val fullFormatter = DateTimeFormatter.ofPattern(FULL_DATE_FORMAT)
        return when (date.length) {
            LESS_TEN_DAY_SHORT_DATE_FORMAT_LENGTH -> LocalDate.parse(date, formatter)
            SHORT_DATE_FORMAT_LENGTH, SHORT_DATE_FORMAT_DELIMITER_LENGTH -> LocalDate.parse(date, shortFormatter)
            else -> LocalDate.parse(date, fullFormatter)
        }
    }

    private fun transformToCorrectYear(year: Int): Int = when {
        year > YEAR_WITHOUT_THOUSAND -> {
            year - TWO_THOUSAND_YEAR
        }
        else -> year
    }

    private fun getCostAllPayments(paymentsList: MutableCollection<Float>): String {
        var costAllPayments = 0f
        paymentsList.forEach { costAllPayments += it }
        return costAllPayments.toString()
    }

    data class CategorizedInfo(val requestYear: String,
                               val requestMonth: String,
                               val costAllPayments: String,
                               val categorizedMap: Map<String, String>)

    data class DateRange(val startYear: Int,
                         val startMonth: Int,
                         val endYear: Int,
                         val endMonth: Int)

    companion object {
        private const val MONTH_POSITION_IN_DATE = 1
        private const val YEAR_POSITION_IN_DATE = 2
        private const val DATE_AND_TIME_DELIMITER = ","
        private const val LESS_TEN_DAY_SHORT_DATE_FORMAT_LENGTH = 6
        private const val SHORT_DATE_FORMAT_LENGTH = 7
        private const val SHORT_DATE_FORMAT_DELIMITER_LENGTH = 8
        private const val LESS_TEN_DAYS_SHORT_DATE_FORMAT = "d.MM.yy"
        private const val SHORT_DATE_FORMAT = "dd.MM.yy"
        private const val FULL_DATE_FORMAT = "dd.MM.yy, HH:mm"

        private const val FIRST_NUMBER_MONTH = 1
        private const val LAST_NUMBER_MONTH = 12

        private const val WITHOUT_SORT_TYPE = 0
        private const val ASC_SORT_TYPE = 1
        private const val DESC_SORT_TYPE = 2

        private const val YEAR_WITHOUT_THOUSAND = 1000
        private const val TWO_THOUSAND_YEAR = 2000
    }
}