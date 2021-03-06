package com.bugtsa.casher.resource.api.controllers.avangard.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class SendAttachmentUIModel(

        @field:JsonProperty("array")
        val ring: ArrayList<AttachmentUIModel>
)
