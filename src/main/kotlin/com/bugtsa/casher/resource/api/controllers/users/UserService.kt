package com.bugtsa.casher.resource.api.controllers.users

import com.bugtsa.casher.resource.api.data.entity.User
import com.bugtsa.casher.resource.api.models.UserDto
import com.bugtsa.casher.resource.api.models.UserDto.Companion.UserDtoEmpty
import com.bugtsa.casher.resource.model.CustomPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    fun getAllUsers(principal: CustomPrincipal): List<User> = userRepository.findAll().toMutableList()
            .filter { user -> user.email != principal.email }

    fun getUsers(listUsersId: List<Int>): List<UserDto> {
        listUsersId.forEach { id ->
            userRepository.findById(id)
        }
        return listUsersId.map { id ->
            UserDto(userRepository.findById(id).get())
        }
    }

    fun getUser(userId: Int): UserDto {
        return userRepository.findById(userId).let { user ->
            when {
                user.isPresent -> UserDto(user.get())
                else -> UserDtoEmpty()
            }
        }
    }

    fun getUser(userName: String): UserDto {
        return userRepository.findAll().find { user -> user.username == userName }?.let { user ->
            UserDto(user)
        } ?: UserDtoEmpty()
    }
}