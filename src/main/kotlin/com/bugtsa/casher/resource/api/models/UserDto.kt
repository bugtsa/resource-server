package com.bugtsa.casher.resource.api.models

import com.bugtsa.casher.resource.api.data.entity.User

class UserDto(user: User) {
    val id: Int = user.id
    val userName: String = user.username
    val email: String = user.email
}