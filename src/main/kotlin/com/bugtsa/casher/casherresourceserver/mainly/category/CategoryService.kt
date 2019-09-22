package com.bugtsa.casher.casherresourceserver.mainly.category

import com.bugtsa.casher.casherresourceserver.mainly.data.entity.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryService {

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    fun getAllCategories() = categoryRepository.findAll().toMutableList()

    fun getCategory(id: Int): Category {
        return categoryRepository.findById(id).get()
    }

    fun addCategory(category: Category): Category {
        return categoryRepository.save(category)
    }

    fun updateCategory(id: Int, newCategory: Category) {
        newCategory.id = id
        categoryRepository.save(newCategory)
    }

    fun deleteCategory(id: Int) {
        categoryRepository.deleteById(id)
    }
}