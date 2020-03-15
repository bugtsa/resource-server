package com.bugtsa.casher.resource.api.data.entity.workout

import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

//@Entity
class Metcon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @OneToMany
    private val metconExercises: MutableList<MetconExercise>

    private val isLegendary = false
    private val isBenchMark = false
    private val cap: Date

    private val especiallyDefinitions: String

    constructor(metconExercises: MutableList<MetconExercise>) {
        this.metconExercises = metconExercises
        this.especiallyDefinitions = ""
        this.cap = Date()
    }

    constructor(metconExercises: MutableList<MetconExercise>, especiallyDefinitions: String, cap: Date) {
        this.metconExercises = metconExercises
        this.especiallyDefinitions = especiallyDefinitions
        this.cap = cap
    }
}