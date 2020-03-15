package com.bugtsa.casher.resource.api.data.entity.workout

import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

//@Entity
class MetconExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    private val exercise: Exercise
    private val repetitions: Int
    private val during: Date
    private val especiallyDefinition: String

    constructor(exercise: Exercise, repetitions: Int, during: Date, especiallyDefinition: String) {
        this.exercise = exercise
        this.repetitions = repetitions
        this.during = during
        this.especiallyDefinition = especiallyDefinition
    }

    constructor(exercise: Exercise, repetitions: Int, during: Date) {
        this.exercise = exercise
        this.repetitions = repetitions
        this.during = during
        this.especiallyDefinition = ""
    }

}