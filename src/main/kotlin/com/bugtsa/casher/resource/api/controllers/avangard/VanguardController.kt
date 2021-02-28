package com.bugtsa.casher.resource.api.controllers.avangard

import com.bugtsa.casher.resource.api.controllers.avangard.VanguardController.Companion.VANGUARD_NAME
import com.bugtsa.casher.resource.api.controllers.avangard.data.AttachmentOrder
import com.bugtsa.casher.resource.api.controllers.avangard.data.AttachmentOrder.Companion.toAttachment
import com.bugtsa.casher.resource.api.controllers.avangard.data.EditOrder
import com.bugtsa.casher.resource.api.controllers.avangard.data.EditOrder.Companion.toEditOrder
import com.bugtsa.casher.resource.api.controllers.avangard.data.OrderFullUIModel
import com.bugtsa.casher.resource.api.controllers.avangard.data.StatusOrder
import com.bugtsa.casher.resource.api.controllers.avangard.data.StatusOrder.Companion.FIRST_ORDER_ID_VALUE
import com.bugtsa.casher.resource.api.controllers.avangard.data.StatusOrder.Companion.SECOND_ORDER_ID_VALUE
import com.bugtsa.casher.resource.api.controllers.avangard.data.StatusOrder.Companion.THIRD_ORDER_ID_VALUE
import com.bugtsa.casher.resource.api.controllers.avangard.data.StatusOrder.Companion.toStatusOrder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(VANGUARD_NAME)
class VanguardController {

    @PostMapping(LOGIN_NAME)
    fun processLogin(@ModelAttribute("login") login: String): ResponseEntity<String> =
            if (login == "")
                ResponseEntity(LOGIN_USER_STRING, HttpStatus.OK)
            else
                ResponseEntity(MINUS_ONE_STRING, HttpStatus.OK)

    @GetMapping(LOGOUT_NAME)
    fun processLogout(): ResponseEntity<String> =
            ResponseEntity(SUCCESS_ANSWER, HttpStatus.OK)

    @GetMapping("$ORDERS_NAME/{statusId}")
    fun processSeenOrders(@PathVariable statusId: Long): ResponseEntity<String> =
            when (statusId.toStatusOrder()) {
                StatusOrder.Seen -> ResponseEntity(ORDERS_STRING, HttpStatus.OK)
                StatusOrder.First -> ResponseEntity(FIRST_ORDER_STRING, HttpStatus.OK)
                StatusOrder.Second -> ResponseEntity(SECOND_ORDER_STRING, HttpStatus.OK)
                StatusOrder.Third -> ResponseEntity(THIRD_ORDER_STRING, HttpStatus.OK)
                StatusOrder.Fail -> ResponseEntity(MINUS_ONE_STRING, HttpStatus.OK)
            }

    @PostMapping("$ORDERS_NAME/{orderId}")
    fun editOrder(
            @PathVariable orderId: Long,
            @RequestBody orderFull: OrderFullUIModel
    ): ResponseEntity<String> =
            when (orderId.toEditOrder(orderFull)) {
                EditOrder.Success -> ResponseEntity(SUCCESS_ANSWER, HttpStatus.OK)
                EditOrder.Fail -> ResponseEntity(MISS_DATA, HttpStatus.OK)
            }

    @GetMapping("/directory")
    fun processDirectory(): ResponseEntity<String> =
            ResponseEntity(DIRECTORY_STRING, HttpStatus.OK)

    @GetMapping("/attachment/{orderId}")
    fun getAttachment(@PathVariable orderId: Long): ResponseEntity<String> =
            when (orderId.toAttachment()) {
                AttachmentOrder.First -> ResponseEntity(FIRST_ATTACHMENTS, HttpStatus.OK)
                AttachmentOrder.Second -> ResponseEntity(SECOND_ATTACHMENTS, HttpStatus.OK)
                AttachmentOrder.Third -> ResponseEntity(THIRD_ATTACHMENTS, HttpStatus.OK)
                AttachmentOrder.Fail -> ResponseEntity(MINUS_ONE_STRING, HttpStatus.OK)

            }

    companion object {

        internal const val VANGUARD_NAME = "/vanguard"
        private const val LOGIN_NAME = "/login"
        private const val LOGOUT_NAME = "/logout"

        private const val ORDERS_NAME = "/orders"

        private const val MINUS_ONE_STRING = "{\n" +
                "    \"error\":\"Произошла ошибка\",\n" +
                "    \"code\":-1\n" +
                "}"

        private const val SUCCESS_ANSWER = "{\n" +
                "\"error\":\"\",\n" +
                "\"code\":0\n" +
                "}\n"

        private const val MISS_DATA = "{\n" +
                "\"error\":\"Отстуствуют данные\",\n" +
                "\"code\":-2\n" +
                "}\n"

        private const val LOGIN_USER_STRING = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\":{\n" +
                "        \"token\":\"ce17bcb2-5015-49e6-a5b5-b4a4b34c8eff\",\n" +
                "        \"login\":\"Test\",\n" +
                "        \"fio\":\"Петров И.И.\"\n" +
                "    }\n" +
                "}"

        private const val FIRST_DATE_TIME = "22.11.2020 10:30:00"
        private const val SECOND_DATE_TIME = "22.01.2021 12:30:00"
        private const val THIRD_DATE_TIME = "22.01.2021 15:30:00"

        private const val FIRST_NUM = "П21/270920"
        private const val SECOND_NUM = "П21/270922"
        private const val THIRD_NUM = "П21/270925"

        private const val FIRST_TEL = "79082524007"
        private const val SECOND_TEL = "79082524008"
        private const val THIRD_TEL = "79082524005"

        private const val FIRST_ADDRESS_NAME = "Мира 33-36"
        private const val SECOND_ADDRESS_NAME = "Мира 33-37"
        private const val THIRD_ADDRESS_NAME = "Мира 35-39"

        private const val FIRST_LAT = "57.997431"
        private const val SECOND_LAT = "57.997432"
        private const val THIRD_LAT = "57.997433"

        private const val FIRST_LONG = "56.238570"
        private const val SECOND_LONG = "56.238571"
        private const val THIRD_LONG = "56.238572"

        private const val FIRST_TYPE_TECH = 1235
        private const val SECOND_TYPE_TECH = 1236
        private const val THIRD_TYPE_TECH = 1237
        private const val FIRST_NAME_TECH = 1266
        private const val SECOND_NAME_TECH = 1267
        private const val THIRD_NAME_TECH = 1268

        private const val ORDERS_STRING = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\": [\n" +
                "            {\n" +
                "                \"datatime\":\"$FIRST_DATE_TIME\",\n" +
                "                \"num\":\"$FIRST_NUM\",\n" +
                "                \"tel\":\"$FIRST_TEL\",\n" +
                "                \"typetech\":$FIRST_TYPE_TECH,\n" +
                "                \"nametech\":$FIRST_NAME_TECH,\n" +
                "                \"adressname\":\"$FIRST_ADDRESS_NAME\",\n" +
                "                \"latitude\":\"$FIRST_LAT\",\n" +
                "                \"longitude\":\"$FIRST_LONG\",\n" +
                "                \"id\":$FIRST_ORDER_ID_VALUE,\n" +
                "                \"status\":0\n" +
                "            },\n" +
                "            {\n" +
                "                \"datatime\":\"$SECOND_DATE_TIME\",\n" +
                "                \"num\":\"$SECOND_NUM\",\n" +
                "                \"tel\":\"$SECOND_TEL\",\n" +
                "                \"typetech\":$SECOND_TYPE_TECH,\n" +
                "                \"nametech\":$SECOND_NAME_TECH,\n" +
                "                \"adressname\":\"$SECOND_ADDRESS_NAME\",\n" +
                "                \"latitude\":\"$SECOND_LAT\",\n" +
                "                \"longitude\":\"$SECOND_LONG\",\n" +
                "                \"id\":$SECOND_ORDER_ID_VALUE,\n" +
                "                \"status\":0\n" +
                "            },\n" +
                "            {\n" +
                "                \"datatime\":\"$THIRD_DATE_TIME\",\n" +
                "                \"num\":\"$THIRD_NUM\",\n" +
                "                \"tel\":\"$THIRD_TEL\",\n" +
                "                \"typetech\":$THIRD_TYPE_TECH,\n" +
                "                \"nametech\":$THIRD_NAME_TECH,\n" +
                "                \"adressname\":\"$THIRD_ADDRESS_NAME\",\n" +
                "                \"latitude\":\"$THIRD_LAT\",\n" +
                "                \"longitude\":\"$THIRD_LONG\",\n" +
                "                \"id\":$THIRD_ORDER_ID_VALUE,\n" +
                "                \"status\":0\n" +
                "            }\n" +
                "        ]\n" +
                "}"

        private const val FIRST_ORDER_STRING = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\":{\n" +
                "       \"datatime\":\"$FIRST_DATE_TIME\",\n" +
                "       \"num\":\"$FIRST_NUM\",\n" +
                "       \"tel\":\"$FIRST_TEL\",\n" +
                "       \"typetech\":$FIRST_TYPE_TECH,\n" +
                "       \"nametech\":$FIRST_NAME_TECH,\n" +
                "       \"adressname\":\"$FIRST_ADDRESS_NAME\",\n" +
                "       \"latitude\":\"$FIRST_LAT\",\n" +
                "       \"longitude\":\"$FIRST_LONG\",\n" +
                "       \"equipment\":\"Без двери\",\n" +
                "       \"defect\":\"Не включается\",\n" +
                "       \"stage\":\"Взять реле\",\n" +
                "       \"status\":0,\n" +
                "       \"date_time_exec\":[\n" +
                "           {\n" +
                "               \"date_exec\":\"22.11.2020 \",\n" +
                "               \"time_start_exec\":\"10:30:00\",\n" +
                "               \"time_end_exec\":\"11:30:00\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"date_exec\":\"23.11.2020 \",\n" +
                "               \"time_start_exec\":\"15:30:00\",\n" +
                "               \"time_end_exec\":\"21:30:00\"\n" +
                "           }\n" +
                "       ]\n" +
                "    }\n" +
                "}"

        private const val SECOND_ORDER_STRING = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\":{\n" +
                "       \"datatime\":\"$SECOND_DATE_TIME\",\n" +
                "       \"num\":\"$SECOND_NUM\",\n" +
                "       \"tel\":\"$SECOND_TEL\",\n" +
                "       \"typetech\":$SECOND_TYPE_TECH,\n" +
                "       \"nametech\":$SECOND_NAME_TECH,\n" +
                "       \"adressname\":\"$SECOND_ADDRESS_NAME\",\n" +
                "       \"latitude\":\"$SECOND_LAT\",\n" +
                "       \"longitude\":\"$SECOND_LONG\",\n" +
                "       \"equipment\":\"Без лестницы\",\n" +
                "       \"defect\":\"Не выкручивается\",\n" +
                "       \"stage\":\"Взять лестницу\",\n" +
                "       \"status\":0,\n" +
                "       \"date_time_exec\":[\n" +
                "           {\n" +
                "               \"date_exec\":\"22.11.2020 \",\n" +
                "               \"time_start_exec\":\"10:30:00\",\n" +
                "               \"time_end_exec\":\"11:30:00\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"date_exec\":\"23.11.2020 \",\n" +
                "               \"time_start_exec\":\"15:30:00\",\n" +
                "               \"time_end_exec\":\"21:30:00\"\n" +
                "           }\n" +
                "       ]\n" +
                "    }\n" +
                "}"

        private const val THIRD_ORDER_STRING = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\":{\n" +
                "       \"datatime\":\"$THIRD_DATE_TIME\",\n" +
                "       \"num\":\"$THIRD_NUM\",\n" +
                "       \"tel\":\"$THIRD_TEL\",\n" +
                "       \"typetech\":$THIRD_TYPE_TECH,\n" +
                "       \"nametech\":$THIRD_NAME_TECH,\n" +
                "       \"adressname\":\"$THIRD_ADDRESS_NAME\",\n" +
                "       \"latitude\":\"$THIRD_LAT\",\n" +
                "       \"longitude\":\"$THIRD_LONG\",\n" +
                "       \"equipment\":\"Без инструментов\",\n" +
                "       \"defect\":\"Не функционирует\",\n" +
                "       \"stage\":\"Взять отвертку\",\n" +
                "       \"status\":0,\n" +
                "       \"date_time_exec\":[\n" +
                "           {\n" +
                "               \"date_exec\":\"22.11.2020 \",\n" +
                "               \"time_start_exec\":\"10:30:00\",\n" +
                "               \"time_end_exec\":\"11:30:00\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"date_exec\":\"23.11.2020 \",\n" +
                "               \"time_start_exec\":\"15:30:00\",\n" +
                "               \"time_end_exec\":\"21:30:00\"\n" +
                "           }\n" +
                "       ]\n" +
                "    }\n" +
                "}"

        private const val DIRECTORY_STRING = "{\n" +
                "  \"error\":\"\",\n" +
                "  \"code\":0,\n" +
                "  \"data\":{\n" +
                "       \"typetech\":[\n" +
                "           {\n" +
                "               \"id_tech\":$FIRST_TYPE_TECH,\n" +
                "               \"tech_specif\":\"Холодильник\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"id_tech\":$SECOND_TYPE_TECH,\n" +
                "               \"tech_specif\":\"Телевизор\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"id_tech\":$THIRD_TYPE_TECH,\n" +
                "               \"tech_specif\":\"Чайник\"\n" +
                "           }\n" +
                "       ],\n" +
                "       \"nametech\":[\n" +
                "           {\n" +
                "               \"id_name\":$FIRST_NAME_TECH,\n" +
                "               \"name_specif\":\"Bosch\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"id_name\":$SECOND_NAME_TECH,\n" +
                "               \"name_specif\":\"Samsung\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"id_name\":$THIRD_NAME_TECH,\n" +
                "               \"name_specif\":\"Haier\"\n" +
                "           }\n" +
                "       ]\n" +
                "    }\n" +
                "}\n"

        private const val FIRST_ATTACHMENTS = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\": [\n" +
                "       {\n" +
                "           \"id_foto\":9876543,\n" +
                "           \"link_foto\":\"https://ibb.co/mNxbDCf\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876544,\n" +
                "           \"link_foto\":\"https://ibb.co/Xsxj0kg\"\n" +
                "       }\n" +
                "    ]" +
                "}\n"

        private const val SECOND_ATTACHMENTS = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\": [\n" +
                "       {\n" +
                "           \"id_foto\":9876545,\n" +
                "           \"link_foto\":\"https://ibb.co/LpbdPZC\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876547,\n" +
                "           \"link_foto\":\"https://ibb.co/C5pBxhb\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876546,\n" +
                "           \"link_foto\":\"https://ibb.co/WghrNxn\"\n" +
                "       }\n" +
                "    ]" +
                "}\n"

        private const val THIRD_ATTACHMENTS = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\": [\n" +
                "       {\n" +
                "           \"id_foto\":9876553,\n" +
                "           \"link_foto\":\"https://ibb.co/wSYPXJP\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876555,\n" +
                "           \"link_foto\":\"https://ibb.co/6w8Y6S5\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876556,\n" +
                "           \"link_foto\":\"https://ibb.co/wJsvr4C\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876554,\n" +
                "           \"link_foto\":\"https://ibb.co/MPXcQ98\"\n" +
                "       }\n" +
                "    ]" +
                "}\n"
    }
}