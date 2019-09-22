package com.bugtsa.casher.casherresourceserver.mainly.data.entity

import com.bugtsa.casher.casherresourceserver.mainly.model.CategoryDto
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    val name: String

    constructor(categoryDto: CategoryDto) {
        this.name = categoryDto.name
    }
}