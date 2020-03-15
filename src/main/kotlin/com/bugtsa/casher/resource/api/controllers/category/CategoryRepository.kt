package com.bugtsa.casher.resource.api.controllers.category

import com.bugtsa.casher.resource.api.data.entity.Category
import org.springframework.data.repository.CrudRepository

interface CategoryRepository: CrudRepository<Category, Int> {
}