package com.bugtsa.casher.resource.api.controllers.payment

import com.bugtsa.casher.resource.api.Constants.Companion.ADMINS_USERS_AUTH
import com.bugtsa.casher.resource.api.data.entity.Payment
import com.bugtsa.casher.resource.api.data.res.PaymentPageRes
import com.bugtsa.casher.resource.api.data.res.PaymentRes
import com.bugtsa.casher.resource.api.models.PaymentDto
import com.bugtsa.casher.resource.api.models.PaymentFullDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class PaymentController {

    @Autowired
    private val paymentService: PaymentService = PaymentService()

    @GetMapping("$PAYMENT_NAME_METHOD/all")
    fun getAllPayments(): ResponseEntity<List<Payment>> =
            ResponseEntity(paymentService.getAllPayments(), HttpStatus.OK)

    @GetMapping("$PAYMENT_NAME_METHOD$PAGE_PAYMENT_NAME_METHOD$LAST_PAGE_PAYMENT_NAME_METHOD")
    @PreAuthorize(ADMINS_USERS_AUTH)
    fun getLastPage(): ResponseEntity<PaymentPageRes> {
        return ResponseEntity(paymentService.getLastPage(), HttpStatus.OK)
    }

    @GetMapping("$PAYMENT_NAME_METHOD/page/prev")
    @PreAuthorize(ADMINS_USERS_AUTH)
    fun getPrevPage(): ResponseEntity<List<Payment>> =
            ResponseEntity(paymentService.getPrevPage(), HttpStatus.OK)

    @GetMapping("$PAYMENT_NAME_METHOD/user")
    fun getPaymentsByUser(@RequestParam("userId") userId: Int) =
            ResponseEntity(paymentService.getPaymentsByUserId(userId), HttpStatus.OK)

    @RequestMapping("$PAYMENT_NAME_METHOD/{paymentId}")
    fun getPayment(@PathVariable paymentId: Int): ResponseEntity<Payment> =
            ResponseEntity(paymentService.getPayment(paymentId), HttpStatus.OK)

    //region ================= Add Payment =================

    @PostMapping("$PAYMENT_NAME_METHOD/full")
    fun addFullPayment(@ModelAttribute(USER_ID_PARAMETER) userId: String,
                       @ModelAttribute(COST_PARAMETER) cost: String,
                       @ModelAttribute(AMOUNT_PARAMETER) amount: String,
                       @ModelAttribute(PRICE_PARAMETER) price: String,
                       @ModelAttribute(BALANCE_PARAMETER) balance: String,
                       @ModelAttribute(DATE_PARAMETER) date: String,
                       @ModelAttribute(CATEGORY_PAYMENT_PARAMETER) category: String,
                       @ModelAttribute(DESCRIPTION_PARAMETER) descriptions: String): ResponseEntity<Payment> {
        val payment = Payment(PaymentFullDto(userId = userId, cost = cost, balance = balance, amount = amount, price = price,
                date = date, category = category, description = descriptions))
        paymentService.addPayment(payment)
        return ResponseEntity(payment, HttpStatus.OK)
    }

    @PostMapping(PAYMENT_NAME_METHOD)
    fun addPayment(@ModelAttribute(USER_ID_PARAMETER) userId: String,
                   @ModelAttribute(COST_PARAMETER) cost: String,
                   @ModelAttribute(DATE_PARAMETER) date: String,
                   @ModelAttribute(CATEGORY_PAYMENT_PARAMETER) category: String): ResponseEntity<PaymentDto> {
        val payment = Payment(PaymentDto(userId = userId, cost = cost, date = date, category = category))
        val newPayment = paymentService.addPayment(payment)
        return ResponseEntity(newPayment, HttpStatus.OK)
    }

    @PostMapping("$PAYMENT_NAME_METHOD/all")
    fun addPaymentsList(@RequestBody payload: MutableList<PaymentRes>): ResponseEntity<String> {
        paymentService.addPayments(payload)
        return ResponseEntity("Super", HttpStatus.OK)
    }

    //endregion

    @PostMapping("$PAYMENT_NAME_METHOD/$BALANCE_PARAMETER/setup")
    fun setupBalance(@ModelAttribute(BALANCE_PARAMETER) balance: String): ResponseEntity<String> {
        val responsePayment = paymentService.setupBalance(balance)
        return when {
            responsePayment >= 0 -> ResponseEntity(responsePayment.toString(), HttpStatus.OK)
            responsePayment == -1 -> ResponseEntity("empty Table", HttpStatus.INTERNAL_SERVER_ERROR)
            responsePayment == -2 -> ResponseEntity("wrong Argument", HttpStatus.UNPROCESSABLE_ENTITY)
            else -> ResponseEntity("Not support Error", HttpStatus.UNPROCESSABLE_ENTITY)
        }
    }

    @PostMapping("$PAYMENT_NAME_METHOD/$BALANCE_PARAMETER/refresh")
    fun refreshBalance(@ModelAttribute(BALANCE_PARAMETER) balance: String): ResponseEntity<String> {
        val responsePayment = paymentService.setupBalance(balance, true)
        return when {
            responsePayment >= 0 -> ResponseEntity(responsePayment.toString(), HttpStatus.OK)
            responsePayment == -1 -> ResponseEntity("empty Table", HttpStatus.INTERNAL_SERVER_ERROR)
            responsePayment == -2 -> ResponseEntity("wrong Argument", HttpStatus.UNPROCESSABLE_ENTITY)
            else -> ResponseEntity("Not support Error", HttpStatus.UNPROCESSABLE_ENTITY)
        }
    }

    @PutMapping("$PAYMENT_NAME_METHOD/{id}")
    fun updatePayment(@RequestBody paymentRes: PaymentRes, @PathVariable id: Int): ResponseEntity<Payment> {
        val payment = Payment(PaymentDto(paymentRes))
        paymentService.updatePayment(id, payment)
        return ResponseEntity(payment, HttpStatus.OK)
    }

    @DeleteMapping("$PAYMENT_NAME_METHOD/{id}")
    fun deletePayment(@PathVariable id: Int): ResponseEntity<Int> {
        paymentService.deletePayment(id)
        return ResponseEntity(id, HttpStatus.OK)
    }

    companion object {
        private const val PAYMENT_NAME_METHOD = "/payment"
        private const val PAGE_PAYMENT_NAME_METHOD = "/page"
        private const val LAST_PAGE_PAYMENT_NAME_METHOD = "/last"
        private const val USER_ID_PARAMETER = "userId"
        private const val COST_PARAMETER = "cost"
        private const val CATEGORY_PAYMENT_PARAMETER = "category"
        private const val DATE_PARAMETER = "date"
        private const val BALANCE_PARAMETER = "balance"
        private const val AMOUNT_PARAMETER = "amount"
        private const val PRICE_PARAMETER = "price"
        private const val DESCRIPTION_PARAMETER = "description"
    }
}