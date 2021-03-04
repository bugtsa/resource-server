package com.bugtsa.casher.resource.api.controllers.avangard.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class SendAttachmentData(

        @field:JsonProperty("foto")
        val foto: String
)