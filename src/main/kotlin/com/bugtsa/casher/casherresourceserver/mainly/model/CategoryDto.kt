package com.bugtsa.casher.casherresourceserver.mainly.model

import com.bugtsa.casher.casherresourceserver.mainly.data.res.CategoryRes

class CategoryDto {

    val name: String

    constructor(name: String) {
        this.name = name
    }

    constructor(categoryRes: CategoryRes) {
        name = categoryRes.name
    }
}