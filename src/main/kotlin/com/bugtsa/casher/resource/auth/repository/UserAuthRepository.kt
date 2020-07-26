package com.bugtsa.casher.resource.auth.repository;

import com.bugtsa.casher.resource.auth.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import javax.transaction.Transactional

@Repository
@Transactional
interface UserAuthRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): User

    fun findByEmail(email: String): User
}


