package com.bugtsa.casher.resource.api.data.entity.workout

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

//@Entity
class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    private val name: String
    private val description: String

    constructor(name: String, description: String) {
        this.name = name
        this.description = description
    }
}