package com.bugtsa.casher.resource.api.data.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id = 0L

    val direction: String

    val type: String

    val description: String

    val value: Long

    constructor(direction: String, value: Long) {
        this.direction = direction
        this.value = value
        this.type = ""
        this.description = ""
    }

    constructor(direction: String, value: Long, type: String, description: String) {
        this.direction = direction
        this.value = value
        this.type = type
        this.description = description
    }
}