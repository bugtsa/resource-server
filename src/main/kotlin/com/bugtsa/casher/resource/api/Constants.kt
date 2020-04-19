package com.bugtsa.casher.resource.api

class Constants {
    companion object {
        const val ADMINS_USERS_AUTH = "hasAnyAuthority('role_admin','role_user')"
        const val ADMINS_AUTH = "hasAuthority('role_admin')"
    }
}