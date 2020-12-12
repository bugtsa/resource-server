package com.bugtsa.casher.resource.api.controllers.payment

import com.bugtsa.casher.resource.api.controllers.category.CategoryService
import com.bugtsa.casher.resource.api.data.entity.Category
import com.bugtsa.casher.resource.api.data.entity.Payment
import com.bugtsa.casher.resource.api.data.entity.Payment.Companion.DATE_AND_TIME_DELIMITER
import com.bugtsa.casher.resource.api.data.entity.Payment.Companion.getDateTimePair
import com.bugtsa.casher.resource.api.data.res.PaymentByDayRes
import com.bugtsa.casher.resource.api.data.res.PaymentPageRes
import com.bugtsa.casher.resource.api.data.res.PaymentPageWarningsRes
import com.bugtsa.casher.resource.api.data.res.PaymentRes
import com.bugtsa.casher.resource.api.models.CategoryDto
import com.bugtsa.casher.resource.api.models.PaymentDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*
import kotlin.collections.HashMap

@Service
class PaymentService {

    @Autowired
    private lateinit var paymentRepository: PaymentRepository

    @Autowired
    private lateinit var categoryService: CategoryService

    fun getAllPayments(): MutableList<Payment> {
        return paymentRepository.findAll().toMutableList()
    }

    private var pageNumber = 0


    fun getSortedMapPaymentsByDay(paymentsByDayMap: HashMap<String, MutableList<PaymentDto>>)
            : SortedMap<String, MutableList<PaymentDto>> = paymentsByDayMap
            .toSortedMap(Comparator { o1, o2 ->
                val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
                try {
                    LocalDate.parse(o1, formatter).compareTo(LocalDate.parse(o2, formatter))
                } catch (e: DateTimeParseException) {
                    throw IllegalArgumentException(e)
                }
            })

    fun getLastPage(): PaymentPageRes {
        pageNumber = 0
        val sortedByIdDesc = PageRequest.of(pageNumber, SIZE_PAGE_REQUEST, Sort(Sort.Direction.DESC, ID_NAME_COLUMN))
        val paymentsList = paymentRepository.findAll(sortedByIdDesc).toList()
        return getPaymentsByDay(paymentsList)
    }

    fun getPrevPage(): List<Payment> {
        pageNumber++
        val sortedByIdDesc = PageRequest.of(pageNumber, SIZE_PAGE_REQUEST, Sort(Sort.Direction.DESC, ID_NAME_COLUMN))
        return paymentRepository.findAll(sortedByIdDesc).toList()
    }

    fun getPayment(id: Int): Payment {
        return paymentRepository.findById(id).get()
    }

    fun getPaymentsByUserId(userId: Int): MutableList<Payment> {
        val list = mutableListOf<Payment>()
        paymentRepository.findAll().forEach { payment ->
            if (payment.userId.toInt() == userId) {
                list.add(payment)
            }
        }
        return list
    }

    fun addPayment(payment: Payment): PaymentDto {
        val paymentDto = PaymentDto(payment)
        paymentDto.categoryId = getCategoryId(payment.category)
        paymentDto.balance = processCurrentBalance(payment)
        val newPayment = Payment(paymentDto)
        findLast()?.content?.last()?.also { lastPayment ->
            newPayment.id = lastPayment.id + 1
        }
        paymentRepository.save(newPayment)
        return processPaymentDto(newPayment)
    }

    fun addPayments(payloadList: MutableList<PaymentRes>) {
        payloadList.forEach { payload ->
            val paymentDto = PaymentDto(payload)
            paymentDto.categoryId = getCategoryId(payload.category)
            paymentRepository.save(Payment(paymentDto))
        }
    }

    fun updatePayment(id: Int, newPayment: Payment): Payment {
        newPayment.id = id
        paymentRepository.save(newPayment)
        return newPayment
    }

    fun deletePayment(id: Int) {
        paymentRepository.deleteById(id)
    }

    private fun findLast(): Page<Payment>? {
        val sortedByIdDesc = PageRequest.of(0, 1, Sort(Sort.Direction.DESC, ID_NAME_COLUMN))
        return paymentRepository.findAll(sortedByIdDesc)
    }

    fun setupBalance(balance: String, isRefreshing: Boolean = false): Int {
        val lastPayment = findLast()?.content?.last()
        val balanceFloat = getBalanceToFloat(balance)
        return if (lastPayment != null && lastPayment.id >= 0 && balanceFloat != 0.0f) {
            val paymentDto = PaymentDto(lastPayment)
            val lastBalance = getBalanceToFloat(lastPayment.balance)
            paymentDto.balance = if (lastBalance != 0.0f && isRefreshing) {
                (balanceFloat + lastBalance).toString()
            } else {
                balance
            }
            val newPayment = Payment(paymentDto)
            paymentRepository.save(newPayment)
            lastPayment.id
        } else {
            -1
        }
    }

    private fun getPaymentsByDay(paymentsList: List<Payment>): PaymentPageRes {
        val paymentsPage = (mutableListOf<PaymentPageWarningsRes>() to hashMapOf<String, MutableList<PaymentDto>>())
                .also { (warningsList, paymentsMapByDay) ->
                    paymentsList.forEach { payment ->
                        val newPayment = processPaymentDto(payment)
                        when {
                            newPayment.date.isEmpty() -> {
                                warningsList.add(PaymentPageWarningsRes("Need setup date for payment", newPayment))
                            }
                            !paymentsMapByDay.contains(newPayment.date) -> {
                                val tempPaymentsList = mutableListOf<PaymentDto>()
                                tempPaymentsList.add(newPayment)
                                paymentsMapByDay[newPayment.date] = tempPaymentsList
                            }
                            else -> {
                                paymentsMapByDay[newPayment.date]?.also {
                                    it.add(0, newPayment)
                                    paymentsMapByDay[newPayment.date] = it
                                }
                            }
                        }
                    }
                }
        return paymentsPage.let { (warningsList, paymentsMapByDay) ->
            PaymentPageRes(
                    hasWarning = warningsList.isNotEmpty(),
                    warningsList = warningsList,
                    page = mutableListOf<PaymentByDayRes>()
                            .let { listPaymentsByDay ->
                                getSortedMapPaymentsByDay(paymentsMapByDay).keys.forEach { key ->
                                    listPaymentsByDay.add(PaymentByDayRes(listPaymentsByDay.size.toString(), key, null))
                                    paymentsMapByDay[key]?.forEach { payment ->
                                        listPaymentsByDay.add(PaymentByDayRes(listPaymentsByDay.size.toString(), null, payment))
                                    }
                                }
                                listPaymentsByDay
                            }
            )
        }
    }

    private fun processPaymentDto(payment: Payment): PaymentDto {
        val rawDate = payment.date
        return when (rawDate.contains(DATE_AND_TIME_DELIMITER)) {
            true -> {
                val (date, time) = getDateTimePair(rawDate)
                PaymentDto(oldPayment = payment, newDate = date, newTime = time)
            }
            false -> PaymentDto(payment = payment)
        }
    }

    private fun getBalanceToFloat(balance: String): Float =
            try {
                balance.toFloat()
            } catch (e: java.lang.NumberFormatException) {
                0.0f
            }

    private fun getCategoryId(paymentCategory: String): Int {
        var foundCategoryId = 0
        var isMissedCategory = true
        categoryService.getAllCategories().forEach { category ->
            if (category.name == paymentCategory) {
                isMissedCategory = false
                foundCategoryId = category.id
            }
        }
        if (isMissedCategory) {
            val newCategory = Category(CategoryDto(paymentCategory))
            val addedCategory = categoryService.addCategory(newCategory)
            foundCategoryId = addedCategory.id
        }
        return foundCategoryId
    }

    private fun processCurrentBalance(payment: Payment): String {
        val lastPayment = findLast()?.content?.last()
        return try {
            lastPayment?.balance?.let { lastBalance ->
                (lastBalance.toFloat() - payment.cost.toFloat()).toString()
            } ?: ""
        } catch (e: java.lang.NumberFormatException) {
            ""
        }
    }

    private fun <K, V> Map<K, V>.reversed() = HashMap<V, K>().also { newMap ->
        entries.forEach { newMap[it.value] = it.key }
    }

    companion object {
        const val DATE_FORMAT = "dd.MM.yy"

        private const val ID_NAME_COLUMN = "id"
        private const val SIZE_PAGE_REQUEST = 30
    }

    sealed class PaymentCategorizedSortType {
        object WITHOUT : PaymentCategorizedSortType()
        object ASC : PaymentCategorizedSortType()
        object DESC : PaymentCategorizedSortType()
    }
}