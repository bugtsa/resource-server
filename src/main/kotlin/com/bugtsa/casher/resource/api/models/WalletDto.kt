package com.bugtsa.casher.resource.api.models

import com.bugtsa.casher.resource.api.data.entity.Wallet

class WalletDto {

    val name: String
    val description: String
    val users: List<UserDto>?

    constructor(
            name: String,
            description: String,
            users: List<UserDto>?
    ) {
        this.name = name
        this.description = description
        this.users = users
    }

    constructor(wallet: Wallet) {
        name = wallet.name
        description = wallet.desc
        users = wallet.users?.map { user ->
            UserDto(user)
        }
    }

    companion object {
        fun WalletDtoEmtpy(): WalletDto {
            return WalletDto("", "", null)
        }
    }
}