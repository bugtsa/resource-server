package com.bugtsa.casher.resource.api.controllers.avangard

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping

class VanguardController {

    @GetMapping("$VANGUARD_NAME$LOGIN_NAME")
    fun processLogin(): ResponseEntity<String> {
        return ResponseEntity("{\n" +
                "\"error\":\"\",\n" +
                "\"code\":0,\n" +
                "\"data\":{\n" +
                "\"token\":\"ce17bcb2-5015-49e6-a5b5-b4a4b34c8eff\",\n" +
                "\"login\":\"Test\",\n" +
                "\"fio\":\"Петров И.И.\"\n" +
                "}\n" +
                "}", HttpStatus.OK)
    }

    companion object {
        private const val VANGUARD_NAME = "/vanguard"
        private const val LOGIN_NAME = "/login"
    }
}