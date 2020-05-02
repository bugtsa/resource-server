package com.bugtsa.casher.resource.api.controllers.wallet

import com.bugtsa.casher.resource.api.data.entity.Wallet
import com.bugtsa.casher.resource.api.models.WalletDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class WalletService {

    @Autowired
    private lateinit var walletRepository: WalletRepository

    fun updateWallet(walletDto: WalletDto): WalletDto {
        val walletId = if (walletRepository.count() > 0) {
            findLast()?.content?.last()?.id ?: DEFAULT_FIRST_ID
        } else {
            DEFAULT_FIRST_ID
        }

        val wallet = Wallet(walletDto)
        wallet.id = walletId

        walletRepository.save(wallet)
        return walletDto
    }

    private fun findLast(): Page<Wallet>? {
        val sortedByIdDesc = PageRequest.of(0, 1, Sort(Sort.Direction.DESC, ID_NAME_COLUMN))
        return walletRepository.findAll(sortedByIdDesc)
    }

    companion object {
        const val ID_NAME_COLUMN = "id"

        private const val DEFAULT_FIRST_ID = 1
    }
}