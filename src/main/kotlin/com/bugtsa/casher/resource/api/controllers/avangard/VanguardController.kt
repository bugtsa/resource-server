package com.bugtsa.casher.resource.api.controllers.avangard

import com.bugtsa.casher.resource.api.controllers.avangard.VanguardController.Companion.LOGOUT_NAME
import com.bugtsa.casher.resource.api.controllers.avangard.VanguardController.Companion.VANGUARD_NAME
import com.bugtsa.casher.resource.api.controllers.avangard.data.StatusOrder
import com.mysql.cj.xdevapi.JsonString
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VanguardController {

    @PostMapping("$VANGUARD_NAME$LOGIN_NAME")
    fun processLogin(@ModelAttribute("login") login: String): ResponseEntity<String> =
        if (login == "test")
            ResponseEntity("{\n" +
                "    \"error\":\"\",\n" +
                "    \"code\":0,\n" +
                "    \"data\":{\n" +
                "        \"token\":\"ce17bcb2-5015-49e6-a5b5-b4a4b34c8eff\",\n" +
                "        \"login\":\"Test\",\n" +
                "        \"fio\":\"Петров И.И.\"\n" +
                "    }\n" +
                "}", HttpStatus.OK)
        else ResponseEntity("{\n" +
                "\"error\":\"Произошла ошибка\",\n" +
                "\"code\":-1\n" +
                "}\n", HttpStatus.OK)

    @GetMapping("$VANGUARD_NAME$LOGOUT_NAME")
    fun processLogout(): ResponseEntity<String> =
            ResponseEntity("{\n" +
                    "\"error\":\"\",\n" +
                    "\"code\":0\n" +
                    "}\n", HttpStatus.OK)

    @GetMapping("$VANGUARD_NAME$ORDERS_NAME/0")
    fun processSeenOrders(): ResponseEntity<String> =
            ResponseEntity("{\n" +
                    "    \"error\":\"\",\n" +
                    "    \"code\":0,\n" +
                    "    \"data\":{\n" +
                    "        \"orders\":[\n" +
                    "            {\n" +
                    "                \"datatime\":\"22.11.2020 10:30:00\",\n" +
                    "                \"num\":\"П21/270920\",\n" +
                    "                \"tel\":\"79082524007\",\n" +
                    "                \"typetech\":1234,\n" +
                    "                \"nametech\":2345,\n" +
                    "                \"adressname\":\"Мира 33-36\",\n" +
                    "                \"latitude\":\"57.997431\",\n" +
                    "                \"longitude\":\"56.238570\",\n" +
                    "                \"id\":5678912354,\n" +
                    "                \"status\":0\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"datatime\":\"22.01.2021 12:30:00\",\n" +
                    "                \"num\":\"П21/270922\",\n" +
                    "                \"tel\":\"79082524008\",\n" +
                    "                \"typetech\":1234,\n" +
                    "                \"nametech\":2345,\n" +
                    "                \"adressname\":\"Мира 33-37\",\n" +
                    "                \"latitude\":\"57.997431\",\n" +
                    "                \"longitude\":\"56.235570\",\n" +
                    "                \"id\":5678912355,\n" +
                    "                \"status\":0\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}", HttpStatus.OK)

    companion object {
        private const val VANGUARD_NAME = "/vanguard"
        private const val LOGIN_NAME = "/login"
        private const val LOGOUT_NAME = "/logout"

        private const val ORDERS_NAME = "/orders"
        private const val SEEN_ORDERS = "/0"
        private const val DETAIL_FIRST = "5678912354"
    }

}