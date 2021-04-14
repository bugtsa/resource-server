package com.bugtsa.casher.resource.api.controllers.avangard.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenUIModel (
    @field:JsonProperty("uuid")
    val uuid: String,
    @field:JsonProperty("token_not")
    val token: String
)