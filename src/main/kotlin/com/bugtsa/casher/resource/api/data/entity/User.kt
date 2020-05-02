package com.bugtsa.casher.resource.api.data.entity

import com.bugtsa.casher.resource.api.models.UserDto
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class User(user: UserDto) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    var username: String = ""
    var email: String = ""

    init {
        id = user.id
        username = user.userName
        email = user.email
    }
}