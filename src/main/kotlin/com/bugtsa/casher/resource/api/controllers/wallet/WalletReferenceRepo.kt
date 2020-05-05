package com.bugtsa.casher.resource.api.controllers.wallet

import com.bugtsa.casher.resource.api.data.entity.Walletreference
import org.springframework.data.repository.CrudRepository

interface WalletReferenceRepo : CrudRepository<Walletreference, Int>