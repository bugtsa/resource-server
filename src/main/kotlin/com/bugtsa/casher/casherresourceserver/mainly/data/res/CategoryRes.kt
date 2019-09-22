package com.bugtsa.casher.casherresourceserver.mainly.data.res

import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryRes (

    @field:JsonProperty( "id")
    val id: Int,

    @field:JsonProperty("name")
    val name: String
)