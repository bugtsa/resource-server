package com.bugtsa.casher.resource.api.data.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Walletreference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    var wallet_id: Int = 0
    var user_id: Int = 0
}