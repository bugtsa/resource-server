package com.bugtsa.casher.resource.api.data.entity

import com.bugtsa.casher.resource.api.models.WalletDto
import javax.persistence.*

@Entity
class Wallet(walletDto: WalletDto) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    var name: String = ""
    var desc: String = ""

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "walletreference", joinColumns = [JoinColumn(name = "wallet_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")])
    var users: List<User>? = null

    init {
        this.name = walletDto.name
        this.desc = walletDto.description
        walletDto.users?.also { userList ->
            this.users = userList.map { userDto ->
                User(userDto)
            }
        }
    }

    companion object {
        fun WalletEmpty(): Wallet {
            return Wallet(WalletDto.WalletDtoEmtpy())
        }
    }
}