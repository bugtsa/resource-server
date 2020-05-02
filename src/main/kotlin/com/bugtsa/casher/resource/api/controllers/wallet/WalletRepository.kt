package com.bugtsa.casher.resource.api.controllers.wallet

import com.bugtsa.casher.resource.api.data.entity.Wallet
import org.springframework.data.repository.PagingAndSortingRepository

interface WalletRepository : PagingAndSortingRepository<Wallet, Int>