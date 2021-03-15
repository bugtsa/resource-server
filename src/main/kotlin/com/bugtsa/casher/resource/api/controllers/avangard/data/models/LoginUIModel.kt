package com.bugtsa.casher.resource.api.controllers.avangard.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginUIModel(
        @field:JsonProperty("login")
        val login: String,
        @field:JsonProperty("password")
        val password: String,
        @field:JsonProperty("code_sms")
        val smsCode: String
)