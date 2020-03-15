package com.bugtsa.casher.resource.api.controllers.charts

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ChartsController {


    @Autowired
    private val chartsService: ChartsService = ChartsService()

    @GetMapping("$CHARTS_NAME_METHOD$CHARTS_DATE_USER_METHOD")
    fun getPaymentsByUserDate(@RequestParam(USER_ID_PARAMETER) userId: Int,
                              @RequestParam(MONTH_PARAMETER) monthNumber: Int,
                              @RequestParam(YEAR_PARAMETER) yearNumber: Int
    ) = ResponseEntity(chartsService.getPaymentsByUserDate(userId, monthNumber, yearNumber), HttpStatus.OK)

    @GetMapping("$CHARTS_NAME_METHOD$CHARTS_DATE_USER_METHOD$CHARTS_CATEGORIZED_METHOD")
    fun getPaymentsByUserDateOutputInfo(@RequestParam(USER_ID_PARAMETER) userId: Int,
                                        @RequestParam(MONTH_PARAMETER) monthNumber: Int,
                                        @RequestParam(YEAR_PARAMETER) yearNumber: Int,
                                        @RequestParam(SORT_PARAMETER) sortType: Int
    ) = ResponseEntity(chartsService.getPaymentsByUserDateOutputInfo(userId, monthNumber, yearNumber, sortType), HttpStatus.OK)

    @GetMapping("$CHARTS_NAME_METHOD$CHARTS_CATEGORIZED_METHOD/range")
    fun getPaymentsByStartAndEndDateRange(@RequestParam(USER_ID_PARAMETER) userId: Int,
                                          @RequestParam(START_MONTH_PARAMETER) startMonth: Int,
                                          @RequestParam(START_YEAR_PARAMETER) startYear: Int,
                                          @RequestParam(END_MONTH_PARAMETER) endMonth: Int,
                                          @RequestParam(END_YEAR_PARAMETER) endYear: Int,
                                          @RequestParam(SORT_PARAMETER) sortType: Int
    ) = ResponseEntity(chartsService.getPaymentsByStartEndRange(userId,
            startMonth,
            startYear,
            endMonth,
            endYear,
            sortType), HttpStatus.OK)

    @GetMapping("${CHARTS_NAME_METHOD}/rangeMonth")
    fun getRangeMonths() = ResponseEntity(chartsService.getRangeMonthAndYear(), HttpStatus.OK)

    companion object {
        private const val USER_ID_PARAMETER = "userId"
        private const val MONTH_PARAMETER = "month"
        private const val YEAR_PARAMETER = "year"
        private const val SORT_PARAMETER = "sort"
        private const val CHARTS_NAME_METHOD = "charts"
        private const val START_MONTH_PARAMETER = "startMonth"
        private const val START_YEAR_PARAMETER = "startYear"
        private const val END_MONTH_PARAMETER = "endMonth"
        private const val END_YEAR_PARAMETER = "endYear"
        private const val CHARTS_DATE_USER_METHOD = "/date&user"
        private const val CHARTS_CATEGORIZED_METHOD = "/categorized"
    }
}