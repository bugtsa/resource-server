package com.bugtsa.casher.resource.api.controllers.wallet

import com.bugtsa.casher.resource.api.controllers.users.UserService
import com.bugtsa.casher.resource.api.models.WalletDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
            @RequestParam("listUsersId") listUsersId: List<Int>) : ResponseEntity<WalletDto> {
        val walletDto = WalletDto(name,
                description,
                userService.getUsers(listUsersId))
        return ResponseEntity(service.updateWallet(walletDto), HttpStatus.OK)
    }

}