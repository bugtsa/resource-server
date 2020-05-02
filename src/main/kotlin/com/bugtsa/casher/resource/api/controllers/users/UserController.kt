package com.bugtsa.casher.resource.api.controllers.users

import com.bugtsa.casher.resource.api.Constants.Companion.ADMINS_AUTH
import com.bugtsa.casher.resource.api.Constants.Companion.ADMINS_USERS_AUTH
import com.bugtsa.casher.resource.api.data.entity.User
import com.bugtsa.casher.resource.model.CustomPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @Autowired
    private val userService = UserService()

    @GetMapping("/admins")
    @PreAuthorize(ADMINS_AUTH)
    fun context(): String {
        val principal = SecurityContextHolder.getContext().authentication
                .principal as CustomPrincipal
        return principal.username + " " + principal.email
    }

    @GetMapping("/users")
    @PreAuthorize(ADMINS_USERS_AUTH)
    fun getUsers(principal: CustomPrincipal): ResponseEntity<List<User>> {
        return ResponseEntity(userService.getAllUsers(principal), HttpStatus.OK)
    }
}