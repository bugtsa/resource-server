package com.bugtsa.casher.resource.api.controllers.avangard

import com.bugtsa.casher.resource.api.controllers.avangard.VanguardController.Companion.VANGUARD_NAME
import com.bugtsa.casher.resource.api.controllers.avangard.data.enums.*
import com.bugtsa.casher.resource.api.controllers.avangard.data.enums.AttachmentOrder.Companion.toAttachment
import com.bugtsa.casher.resource.api.controllers.avangard.data.enums.EditOrder.Companion.toEditOrder
import com.bugtsa.casher.resource.api.controllers.avangard.data.enums.OrderPageSet.Companion.FIRST_ORDER_ID_VALUE
import com.bugtsa.casher.resource.api.controllers.avangard.data.enums.OrderPageSet.Companion.SECOND_ORDER_ID_VALUE
import com.bugtsa.casher.resource.api.controllers.avangard.data.enums.OrderPageSet.Companion.THIRD_ORDER_ID_VALUE
import com.bugtsa.casher.resource.api.controllers.avangard.data.enums.OrderPageSet.Companion.toOrder
import com.bugtsa.casher.resource.api.controllers.avangard.data.enums.OrderPageSet.First
import com.bugtsa.casher.resource.api.controllers.avangard.data.enums.OrderPageSet.Second
import com.bugtsa.casher.resource.api.controllers.avangard.data.enums.OrderPageSet.Third
import com.bugtsa.casher.resource.api.controllers.avangard.data.enums.OrderSet.Companion.toOrderStatus
import com.bugtsa.casher.resource.api.controllers.avangard.data.enums.StatusOrderType.Companion.toStatusOrderType
import com.bugtsa.casher.resource.api.controllers.avangard.data.models.*
import com.bugtsa.casher.resource.api.controllers.avangard.utils.DataUtils.getHoursAndMinutes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@RestController
@RequestMapping(VANGUARD_NAME)
class VanguardController {

    @PostMapping(LOGIN_NAME)
    fun processLogin(@RequestBody loginModel: LoginUIModel): ResponseEntity<String> =
            when {
                loginModel.login == SUCCESS_FIELD_VALUE && loginModel.password == SUCCESS_FIELD_VALUE &&
                        loginModel.smsCode.isEmpty() ->
                    ResponseEntity(SUCCESS_ANSWER, HttpStatus.OK)
                (loginModel.login != SUCCESS_FIELD_VALUE || loginModel.password != SUCCESS_FIELD_VALUE) &&
                        loginModel.smsCode.isEmpty() ->
                    ResponseEntity(MINUS_ONE_STRING, HttpStatus.OK)
                loginModel.login == SUCCESS_FIELD_VALUE && loginModel.smsCode == SMS_CODE_SUCCESS_VALUE &&
                        loginModel.password.isEmpty() ->
                    ResponseEntity(LOGIN_USER_STRING, HttpStatus.OK)
                loginModel.login == SUCCESS_FIELD_VALUE && loginModel.smsCode != SMS_CODE_SUCCESS_VALUE &&
                        loginModel.password.isEmpty() ->
                    ResponseEntity(SMS_SEND_ERROR, HttpStatus.OK)
                else -> ResponseEntity(MINUS_ONE_STRING, HttpStatus.OK)
            }

    @GetMapping(LOGOUT_NAME)
    fun processLogout(): ResponseEntity<String> =
            ResponseEntity(SUCCESS_ANSWER, HttpStatus.OK)

    @GetMapping("$ORDERS_NAME/{orderId}")
    fun processSeenOrders(@PathVariable orderId: Long): ResponseEntity<String> =
            when (orderId) {
                0L -> ResponseEntity(ORDERS_STRING, HttpStatus.OK)
                else -> ResponseEntity(MINUS_ONE_STRING, HttpStatus.OK)
            }


    @GetMapping("$DETAIL_ORDER_NAME/{orderId}")
    fun getDetailOrder(@PathVariable orderId: Long): ResponseEntity<String> =
            when (orderId.toOrder()) {
                First -> ResponseEntity(FIRST_ORDER_STRING, HttpStatus.OK)
                Second -> ResponseEntity(SECOND_ORDER_STRING, HttpStatus.OK)
                Third -> ResponseEntity(THIRD_ORDER_STRING, HttpStatus.OK)
                OrderPageSet.Fail -> ResponseEntity(MINUS_ONE_STRING, HttpStatus.OK)
            }

    @PostMapping("$DETAIL_ORDER_NAME/{orderId}")
    fun editOrder(
            @PathVariable orderId: Long,
            @RequestBody orderFull: OrderFullUIModel
    ): ResponseEntity<String> =
            when (orderId.toEditOrder(orderFull)) {
                EditOrder.Success -> ResponseEntity(SUCCESS_ANSWER, HttpStatus.OK)
                EditOrder.Fail -> ResponseEntity(MISS_DATA, HttpStatus.OK)
            }

//    @PostMapping("$ORDERS_NAME/status/{orderId}/{statusType}")
//    fun setupStatus(
//            @PathVariable orderId: Long,
//            @PathVariable statusType: Int
//    ): ResponseEntity<String> =
//            when (orderId.toOrderStatus()) {
//                OrderSet.First, OrderSet.Second, OrderSet.Third -> {
//                    when (statusType.toStatusOrderType()) {
//                        StatusOrderType.Open, StatusOrderType.InWork, StatusOrderType.Cancel, StatusOrderType.Waiting,
//                        StatusOrderType.Ready, StatusOrderType.Complete -> ResponseEntity(SUCCESS_ANSWER, HttpStatus.OK)
//                        StatusOrderType.Fail -> ResponseEntity(MISS_DATA, HttpStatus.OK)
//                    }
//                }
//                OrderSet.Fail -> ResponseEntity(MISS_DATA, HttpStatus.OK)
//            }

    @GetMapping("/directory")
    fun processDirectory(): ResponseEntity<String> =
            ResponseEntity(DIRECTORY_STRING, HttpStatus.OK)

    @GetMapping("$ATTACHMENT_NAME/{orderId}")
    fun getAttachment(@PathVariable orderId: Long): ResponseEntity<String> =
            when (orderId.toAttachment()) {
                AttachmentOrder.First -> ResponseEntity(FIRST_ATTACHMENTS, HttpStatus.OK)
                AttachmentOrder.Second -> ResponseEntity(SECOND_ATTACHMENTS, HttpStatus.OK)
                AttachmentOrder.Third -> ResponseEntity(THIRD_ATTACHMENTS, HttpStatus.OK)
                AttachmentOrder.Fail -> ResponseEntity(MINUS_ONE_STRING, HttpStatus.OK)
            }

    @PostMapping("$ATTACHMENT_NAME/{orderId}")
    fun saveAttachment(
            @PathVariable orderId: Long,
            @RequestBody sendAttachmentUIModel: SendAttachmentUIModel
    ): ResponseEntity<String> {
        val arrayResponse = listOf(
                FIRST_SAVED_ATTACHMENTS,
                SECOND_SAVED_ATTACHMENTS,
                THIRD_SAVED_ATTACHMENTS,
                FOUR_SAVED_ATTACHMENTS
        )
        val randomPos = Random.nextInt(0, arrayResponse.size - 1)
        return ResponseEntity(arrayResponse[randomPos], HttpStatus.OK)
    }

    @PostMapping("/notificationGeneral")
    fun setupGeneralNotification(
            @RequestBody notificationUIModel: NotificationUIModel
    ): ResponseEntity<String> {
        val (hours, minutes) = getHoursAndMinutes(notificationUIModel.time)
        return when {
            hours == 1 -> ResponseEntity(SUCCESS_ANSWER, HttpStatus.OK)
            minutes == 30 -> ResponseEntity(TOKEN_ERROR, HttpStatus.OK)
            else -> ResponseEntity(MINUS_ONE_STRING, HttpStatus.OK)
        }
    }

    @PutMapping("$NOTIFICATION_NAME")
    fun setupToken(
            @RequestBody token: TokenUIModel
    ): ResponseEntity<String> {
        return when {
            token.token.isNotEmpty() && token.uuid.isNotEmpty() -> ResponseEntity(SUCCESS_ANSWER, HttpStatus.OK)
            token.token.isEmpty() -> ResponseEntity(TOKEN_ERROR, HttpStatus.OK)
            else -> ResponseEntity(MINUS_ONE_STRING, HttpStatus.OK)
        }
    }

    @PostMapping("/notificationOrder/{orderId}")
    fun setupOrderNotification(
            @PathVariable orderId: Long,
            @RequestBody notificationUIModel: OrderNotificationUIModel
    ): ResponseEntity<String> {
        val (_, minutes) = getHoursAndMinutes(notificationUIModel.time)
        val idOrder = orderId.toOrder()
        return when {
            (idOrder is First || idOrder is Second || idOrder is Third).not() ->
                ResponseEntity(MINUS_ONE_STRING, HttpStatus.OK)
            minutes == 30 -> ResponseEntity(TOKEN_ERROR, HttpStatus.OK)
            else -> ResponseEntity(SUCCESS_ANSWER, HttpStatus.OK)
        }
    }

    companion object {

        internal const val SUCCESS_FIELD_VALUE = "test"
        internal const val SMS_CODE_SUCCESS_VALUE = "4567"
        internal const val FAILED_FIELD_VALUE = "1error23"
        internal const val VANGUARD_NAME = "/vanguard"

        private const val LOGIN_NAME = "/login"

        private const val LOGOUT_NAME = "/logout"
        private const val ORDERS_NAME = "/orders"
        private const val DETAIL_ORDER_NAME = "detailOrder"

        private const val ATTACHMENT_NAME = "/attachment"

        private const val NOTIFICATION_NAME = "/notification"

        private const val SUCCESS_ANSWER = "{\n" +
                "\"error\":\"\",\n" +
                "\"code\":0\n" +
                "}\n"

        private const val MINUS_ONE_STRING = "{\n" +
                "    \"error\":\"Произошла ошибка\",\n" +
                "    \"code\":-1\n" +
                "}"

        private const val MISS_DATA = "{\n" +
                "\"error\":\"Отстуствуют данные\",\n" +
                "\"code\":-2\n" +
                "}\n"

        private const val TOKEN_ERROR = "{\n" +
                "    \"error\":\"Ошибка токена\",\n" +
                "    \"code\":-3\n" +
                "}"

        private const val SMS_SEND_ERROR = "{\n" +
                "    \"error\":\"Произошла ошибка при отправке SMS\",\n" +
                "    \"code\":-4\n" +
                "}"

        private const val LOGIN_USER_STRING = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\":{\n" +
                "        \"token\":\"ce17bcb2-5015-49e6-a5b5-b4a4b34c8eff\",\n" +
                "        \"login\":\"Test\",\n" +
                "        \"fio\":\"Петров И.И.\"\n" +
                "    }\n" +
                "}"

        private const val FIRST_DATE_TIME = "02.04.2021 10:30:00"
        private const val SECOND_DATE_TIME = "04.04.2021 12:30:00"
        private const val THIRD_DATE_TIME = "02.04.2021 15:30:00"

        private const val FIRST_NUM = "П21/270920"
        private const val SECOND_NUM = "П21/270922"
        private const val THIRD_NUM = "П21/270925"

        private const val FIRST_TEL = "79082524007"
        private const val SECOND_TEL = "79082524008"
        private const val THIRD_TEL = "79082524005"

        private const val FIRST_ADDRESS_NAME = "Мира 33-36"
        private const val SECOND_ADDRESS_NAME = "Мира 33-37"
        private const val THIRD_ADDRESS_NAME = "Мира 35-39"

        private const val FIRST_LAT = "55.764165"
        private const val SECOND_LAT = "55.757140"
        private const val THIRD_LAT = "55.751703"

        private const val FIRST_LONG = "37.631199"
        private const val SECOND_LONG = "37.611942"
        private const val THIRD_LONG = "37.627169"

        private const val FIRST_FRIZ_TYPE_TECH = 1235
        private const val SECOND_TV_TYPE_TECH = 1236
        private const val THIRD_HOTSPOT_TYPE_TECH = 1237
        private const val FIRST_BOSCH_NAME_TECH = 1266
        private const val SECOND_SAM_NAME_TECH = 1267
        private const val THIRD_HAR_NAME_TECH = 1268

        private const val ORDERS_STRING = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\": [\n" +
                "            {\n" +
                "                \"datatime\":\"$FIRST_DATE_TIME\",\n" +
                "                \"num\":\"$FIRST_NUM\",\n" +
                "                \"tel\":\"$FIRST_TEL\",\n" +
                "                \"typetech\":$FIRST_FRIZ_TYPE_TECH,\n" +
                "                \"nametech\":$FIRST_BOSCH_NAME_TECH,\n" +
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
                "                \"typetech\":$SECOND_TV_TYPE_TECH,\n" +
                "                \"nametech\":$SECOND_SAM_NAME_TECH,\n" +
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
                "                \"typetech\":$THIRD_HOTSPOT_TYPE_TECH,\n" +
                "                \"nametech\":$THIRD_HAR_NAME_TECH,\n" +
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
                "       \"typetech\":$FIRST_FRIZ_TYPE_TECH,\n" +
                "       \"nametech\":$FIRST_BOSCH_NAME_TECH,\n" +
                "       \"adressname\":\"$FIRST_ADDRESS_NAME\",\n" +
                "       \"latitude\":\"$FIRST_LAT\",\n" +
                "       \"longitude\":\"$FIRST_LONG\",\n" +
                "       \"equipment\":\"Без двери\",\n" +
                "       \"defect\":\"Не включается\",\n" +
                "       \"stage\":\"Взять реле\",\n" +
                "       \"status\":0,\n" +
                "       \"date_time_exec\":[\n" +
                "           {\n" +
                "               \"date_exec\":\"11.04.2021 \",\n" +
                "               \"time_start_exec\":\"10:30:00\",\n" +
                "               \"time_end_exec\":\"11:30:00\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"date_exec\":\"11.04.2021 \",\n" +
                "               \"time_start_exec\":\"18:30:00\",\n" +
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
                "       \"typetech\":$SECOND_TV_TYPE_TECH,\n" +
                "       \"nametech\":$SECOND_SAM_NAME_TECH,\n" +
                "       \"adressname\":\"$SECOND_ADDRESS_NAME\",\n" +
                "       \"latitude\":\"$SECOND_LAT\",\n" +
                "       \"longitude\":\"$SECOND_LONG\",\n" +
                "       \"equipment\":\"Без лестницы\",\n" +
                "       \"defect\":\"Не выкручивается\",\n" +
                "       \"stage\":\"Взять лестницу\",\n" +
                "       \"status\":0,\n" +
                "       \"date_time_exec\":[\n" +
                "           {\n" +
                "               \"date_exec\":\"11.04.2021 \",\n" +
                "               \"time_start_exec\":\"11:45:00\",\n" +
                "               \"time_end_exec\":\"12:30:00\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"date_exec\":\"11.04.2021 \",\n" +
                "               \"time_start_exec\":\"14:30:00\",\n" +
                "               \"time_end_exec\":\"15:30:00\"\n" +
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
                "       \"typetech\":$THIRD_HOTSPOT_TYPE_TECH,\n" +
                "       \"nametech\":$THIRD_HAR_NAME_TECH,\n" +
                "       \"adressname\":\"$THIRD_ADDRESS_NAME\",\n" +
                "       \"latitude\":\"$THIRD_LAT\",\n" +
                "       \"longitude\":\"$THIRD_LONG\",\n" +
                "       \"equipment\":\"Без инструментов\",\n" +
                "       \"defect\":\"Не функционирует\",\n" +
                "       \"stage\":\"Взять отвертку\",\n" +
                "       \"status\":0,\n" +
                "       \"date_time_exec\":[\n" +
                "           {\n" +
                "               \"date_exec\":\"11.04.2021 \",\n" +
                "               \"time_start_exec\":\"18:30:00\",\n" +
                "               \"time_end_exec\":\"19:30:00\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"date_exec\":\"11.04.2021 \",\n" +
                "               \"time_start_exec\":\"20:30:00\",\n" +
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
                "               \"id_tech\":$FIRST_FRIZ_TYPE_TECH,\n" +
                "               \"tech_specif\":\"Холодильник\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"id_tech\":$SECOND_TV_TYPE_TECH,\n" +
                "               \"tech_specif\":\"Телевизор\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"id_tech\":$THIRD_HOTSPOT_TYPE_TECH,\n" +
                "               \"tech_specif\":\"Чайник\"\n" +
                "           }\n" +
                "       ],\n" +
                "       \"nametech\":[\n" +
                "           {\n" +
                "               \"id_name\":$FIRST_BOSCH_NAME_TECH,\n" +
                "               \"name_specif\":\"Bosch\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"id_name\":$SECOND_SAM_NAME_TECH,\n" +
                "               \"name_specif\":\"Samsung\"\n" +
                "           },\n" +
                "           {\n" +
                "               \"id_name\":$THIRD_HAR_NAME_TECH,\n" +
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
                "           \"link_foto\":\"https://upload.wikimedia.org/wikipedia/commons/7/70/Jfk2.jpg\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876544,\n" +
                "           \"link_foto\":\"https://i.stack.imgur.com/T476i.png\"\n" +
                "       }\n" +
                "    ]" +
                "}\n"

        private const val SECOND_ATTACHMENTS = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\": [\n" +
                "       {\n" +
                "           \"id_foto\":9876545,\n" +
                "           \"link_foto\":\"https://i.stack.imgur.com/T476i.png\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876547,\n" +
                "           \"link_foto\":\"https://i.stack.imgur.com/VGGG3.png\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876546,\n" +
                "           \"link_foto\":\"https://upload.wikimedia.org/wikipedia/commons/7/70/Jfk2.jpg\"\n" +
                "       }\n" +
                "    ]" +
                "}\n"

        private const val THIRD_ATTACHMENTS = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\": [\n" +
                "       {\n" +
                "           \"id_foto\":9876553,\n" +
                "           \"link_foto\":\"https://tse1.mm.bing.net/th?id=OIP.9dNMVRmk1a3edFYrwzcFeQHaIi&pid=Api\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876555,\n" +
                "           \"link_foto\":\"https://i.stack.imgur.com/VGGG3.png\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876556,\n" +
                "           \"link_foto\":\"https://i.stack.imgur.com/T476i.png\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876554,\n" +
                "           \"link_foto\":\"https://upload.wikimedia.org/wikipedia/commons/7/70/Jfk2.jpg\"\n" +
                "       }\n" +
                "    ]" +
                "}\n"

        private const val FIRST_SAVED_ATTACHMENTS = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\": [\n" +
                "       {\n" +
                "           \"id_foto\":9876543,\n" +
                "           \"link_foto\":\"https://i.imgur.com/OvuyN79.jpg\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876544,\n" +
                "           \"link_foto\":\"https://i.imgur.com/MCkRuvG.jpg\"\n" +
                "       }\n" +
                "    ]" +
                "}\n"

        private const val SECOND_SAVED_ATTACHMENTS = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\": [\n" +
                "       {\n" +
                "           \"id_foto\":9876545,\n" +
                "           \"link_foto\":\"https://i.imgur.com/huBcOmN.jpg\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876547,\n" +
                "           \"link_foto\":\"https://i.imgur.com/imX6ORl.jpg\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876546,\n" +
                "           \"link_foto\":\"https://i.imgur.com/nVo66gE.jpg\"\n" +
                "       }\n" +
                "    ]" +
                "}\n"

        private const val THIRD_SAVED_ATTACHMENTS = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\": [\n" +
                "       {\n" +
                "           \"id_foto\":9876553,\n" +
                "           \"link_foto\":\"https://i.imgur.com/nnYBLlm.jpg\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876555,\n" +
                "           \"link_foto\":\"https://i.imgur.com/VtFWuZI.jpg\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876556,\n" +
                "           \"link_foto\":\"https://i.imgur.com/Xvnkwhx.jpg\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876554,\n" +
                "           \"link_foto\":\"https://i.imgur.com/j8KrdjY.jpg\"\n" +
                "       }\n" +
                "    ]" +
                "}\n"

        private const val FOUR_SAVED_ATTACHMENTS = "{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\": [\n" +
                "       {\n" +
                "           \"id_foto\":9876553,\n" +
                "           \"link_foto\":\"https://i.imgur.com/E7NFyAu.jpg\"\n" +
                "       },\n" +
                "       {\n" +
                "           \"id_foto\":9876554,\n" +
                "           \"link_foto\":\"https://i.imgur.com/g9CN4XH.jpeg\"\n" +
                "       }\n" +
                "    ]" +
                "}\n"
    }
}