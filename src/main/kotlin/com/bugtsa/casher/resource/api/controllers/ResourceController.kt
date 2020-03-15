package com.bugtsa.casher.resource.api.controllers

import com.bugtsa.casher.resource.api.Constants.Companion.ADMINS_USERS_AUTH
import com.bugtsa.casher.resource.model.CustomPrincipal
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ResourceController {

    @GetMapping("/admins")
    @PreAuthorize("hasAuthority('role_admin')")
    fun context(): String {
        val principal = SecurityContextHolder.getContext().authentication
                .principal as CustomPrincipal
        return principal.username + " " + principal.email
    }

    @GetMapping("/users")
    @PreAuthorize(ADMINS_USERS_AUTH)
    fun secured(principal: CustomPrincipal): String {
        return principal.username + " " + principal.email
    }

    @GetMapping("/common")
    fun general(): String {
        return "common api success"
    }
}