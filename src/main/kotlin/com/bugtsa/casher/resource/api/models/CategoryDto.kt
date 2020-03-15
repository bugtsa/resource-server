package com.bugtsa.casher.resource.api.models

import com.bugtsa.casher.resource.api.data.res.CategoryRes


class CategoryDto {

    val name: String

    constructor(name: String) {
        this.name = name
    }

    constructor(categoryRes: CategoryRes) {
        name = categoryRes.name
    }
}