package com.example.coinwatch.domain

import com.example.coinwatch.data.repository.CoinRepository
import javax.inject.Inject

class UpdateFavoriteStatusUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(coinId: String, isFavorite: Boolean) {
        repository.updateFavoriteStatus(coinId, isFavorite)
    }
}
