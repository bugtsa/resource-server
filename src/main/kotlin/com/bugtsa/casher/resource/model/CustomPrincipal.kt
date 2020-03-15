package com.bugtsa.casher.resource.model

import java.io.Serializable

class CustomPrincipal : Serializable {
    var username: String? = null
    var email: String? = null

    constructor(username: String, email: String) {
        this.username = username
        this.email = email
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}