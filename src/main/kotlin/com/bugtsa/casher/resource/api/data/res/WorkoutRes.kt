package com.bugtsa.casher.resource.api.data.res

import com.fasterxml.jackson.annotation.JsonProperty

data class WorkoutRes (
    @field:JsonProperty("name")
    val name: String,

    @field:JsonProperty("description")
    val description: String,

    @field:JsonProperty("id")
    val id: Int
)