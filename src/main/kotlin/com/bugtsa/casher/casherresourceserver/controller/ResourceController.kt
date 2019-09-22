package com.bugtsa.casher.casherresourceserver.controller

import com.bugtsa.casher.casherresourceserver.model.CustomPrincipal
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
    @PreAuthorize("hasAnyAuthority('role_admin','role_user')")
    fun secured(principal: CustomPrincipal): String {
        return principal.username + " " + principal.email
    }

    @GetMapping("/common")
    fun general(): String {
        return "common api success"
    }

}