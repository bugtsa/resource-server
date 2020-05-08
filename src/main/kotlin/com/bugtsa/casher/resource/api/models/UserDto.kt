package com.bugtsa.casher.resource.api.models

import com.bugtsa.casher.resource.api.data.entity.User

class UserDto {
    val id: Int
    val userName: String
    val email: String

    constructor(user: User) {
        this.id = user.id
        this.userName = user.username
        this.email = user.email
    }

    constructor() {
        id = 0
        userName = ""
        email = ""
    }

    companion object {
        fun UserDtoEmpty() = UserDto()
    }

}