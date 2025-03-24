package com.example.coinwatch.domain

import com.example.coinwatch.data.repository.CoinRepository
import javax.inject.Inject

class FetchCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke() {
        repository.fetchCoins()
    }
}
