package com.bugtsa.casher.resource.api.controllers.users

import com.bugtsa.casher.resource.api.data.entity.User
import com.bugtsa.casher.resource.model.CustomPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    fun getAllUsers(principal: CustomPrincipal): List<User> = userRepository.findAll().toMutableList()
            .filter { user -> user.email != principal.email }

}