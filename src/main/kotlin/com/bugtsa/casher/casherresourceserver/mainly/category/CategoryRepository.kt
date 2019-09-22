package com.bugtsa.casher.casherresourceserver.mainly.category

import com.bugtsa.casher.casherresourceserver.mainly.data.entity.Category
import org.springframework.data.repository.CrudRepository

interface CategoryRepository: CrudRepository<Category, Int> {
}