package com.bugtsa.casher.resource.api.controllers.avangard.data.models

@kotlinx.serialization.Serializable
data class LoginUIModel(
//        @field:JsonProperty("login")
        val login: String,
//        @field:JsonProperty("password")
        val password: String
)