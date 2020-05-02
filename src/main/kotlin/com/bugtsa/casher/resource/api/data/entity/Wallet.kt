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
    @JoinTable(name = "wallet_user", joinColumns = [JoinColumn(name = "wallet_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")])
    private var users: List<User>? = null

    init {
        this.name = walletDto.name
        this.desc = walletDto.description
        walletDto.user?.also { userList ->
            this.users = userList.map { userDto ->
                User(userDto)
            }
        }
    }
}