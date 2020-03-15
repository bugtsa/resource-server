package com.bugtsa.casher.resource.api.controllers.category

import com.bugtsa.casher.resource.api.data.entity.Category
import com.bugtsa.casher.resource.api.data.res.CategoryRes
import com.bugtsa.casher.resource.api.models.CategoryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CategoryController {

    companion object {
        private const val CATEGORY_NAME_METHOD = "/category"
        private const val NAME_PARAMETER = "name"
    }

    @Autowired
    private val categoryService = CategoryService()

    @GetMapping(CATEGORY_NAME_METHOD)
    fun getAllCategories(): ResponseEntity<MutableList<Category>> {
        return ResponseEntity(categoryService.getAllCategories(), HttpStatus.OK)
    }

    @RequestMapping("$CATEGORY_NAME_METHOD/{id}")
    fun getCategory(@PathVariable id: Int): ResponseEntity<Category> {
        return ResponseEntity(categoryService.getCategory(id), HttpStatus.OK)
    }

    @PostMapping(CATEGORY_NAME_METHOD)
    fun addCategory(@ModelAttribute(NAME_PARAMETER) name: String): ResponseEntity<Category> {
        val category = Category(CategoryDto(name))
        categoryService.addCategory(category)
        return ResponseEntity(category, HttpStatus.OK)
    }

    @PutMapping("$CATEGORY_NAME_METHOD/{id}")
    fun updateCategory(@RequestBody categoryRes: CategoryRes, @PathVariable id: Int): ResponseEntity<Category> {
        val category = Category(CategoryDto(categoryRes))
        categoryService.updateCategory(id, category)
        return ResponseEntity(category, HttpStatus.OK)
    }

    @DeleteMapping("$CATEGORY_NAME_METHOD/{id}")
    fun deleteCategory(@PathVariable id: Int): ResponseEntity<Int> {
        categoryService.deleteCategory(id)
        return ResponseEntity(id, HttpStatus.OK)
    }
}