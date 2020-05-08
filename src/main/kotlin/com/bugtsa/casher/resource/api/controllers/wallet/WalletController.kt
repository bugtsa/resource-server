package com.bugtsa.casher.resource.api.controllers.wallet

import com.bugtsa.casher.resource.api.controllers.users.UserService
import com.bugtsa.casher.resource.api.models.UserDto
import com.bugtsa.casher.resource.api.models.WalletDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class WalletController {

    @Autowired
    private val service = WalletService()

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/wallet")
    fun updateWallet(
            @RequestParam("name") name: String,
            @RequestParam("desc") description: String,
            @RequestParam("listUsersId") listUsersId: List<Int>): ResponseEntity<WalletDto> {
        val walletDto = WalletDto(name,
                description,
                userService.getUsers(listUsersId))
        return ResponseEntity(service.updateWallet(walletDto), HttpStatus.OK)
    }

    @GetMapping("/wallets")
    fun getWallets(
            @RequestParam("userId", required = false, defaultValue = USER_ID_DEFAULT_VALUE) userId: Int,
            @RequestParam("userName", required = false, defaultValue = USER_NAME_DEFAULT_VALUE) userName: String
    ): ResponseEntity<Any> {
        val user = performFindUser(performParamTypes(userId, userName), userId, userName)
        return with(user) {
            when {
                this.isEmpty() -> ResponseEntity("Don`t found user with that params", HttpStatus.OK)
                else -> ResponseEntity(service.getWallets(user), HttpStatus.OK)
            }
        }
    }

    private fun performParamTypes(userId: Int, userName: String): ParamUserType {
        return when {
            userName.isNotEmpty() && userId <= 0 -> ParamUserType.Name
            userId > 0 && userName.isEmpty() -> ParamUserType.Id
            userName.isNotEmpty() && userId > 0 -> ParamUserType.IdFirst
            else -> ParamUserType.Unknown
        }
    }

    private fun performFindUser(paramUserType: ParamUserType,
                                userId: Int, userName: String): UserDto {
        return when (paramUserType) {
            ParamUserType.Id -> userService.getUser(userId)
            ParamUserType.Name -> userService.getUser(userName)
            ParamUserType.IdFirst -> {
                val byId = userService.getUser(userId)
                val byName = userService.getUser(userName)
                when {
                    byId.isEmpty().not() -> {
                        byId
                    }
                    byName.isEmpty().not() -> {
                        byName
                    }
                    else -> {
                        UserDto.UserDtoEmpty()
                    }
                }
            }
            ParamUserType.Unknown -> UserDto.UserDtoEmpty()
        }
    }

    fun UserDto.isEmpty() = id == 0 && userName.isEmpty() && email.isEmpty()

    companion object {
        private const val USER_ID_DEFAULT_VALUE = "0"
        private const val USER_NAME_DEFAULT_VALUE = ""
    }
}

sealed class ParamUserType {
    object Id : ParamUserType()
    object Name : ParamUserType()
    object IdFirst : ParamUserType()
    object Unknown : ParamUserType()
}