package com.bugtsa.casher.resource.api.controllers.users

import com.bugtsa.casher.resource.api.data.entity.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Int>