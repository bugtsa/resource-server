package com.bugtsa.casher.resource.api.data.entity.workout

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    private val name: String
    private val englishName: String
    private val englishShortName: String
    private val description: String
    private val englishDescription: String

    constructor(name: String, englishName: String, englishShortName: String, description: String, englishDescription: String) {
        this.name = name
        this.englishName = englishName
        this.englishShortName = englishShortName
        this.description = description
        this.englishDescription = englishDescription
    }

    constructor(name: String, englishName: String, englishShortName: String) {
        this.name = name
        this.englishName = englishName
        this.englishShortName = englishShortName
        this.description = ""
        this.englishDescription = ""
    }

    constructor(name: String, englishName: String) {
        this.name = name
        this.englishName = englishName
        this.englishShortName = ""
        this.description = ""
        this.englishDescription = ""
    }
}