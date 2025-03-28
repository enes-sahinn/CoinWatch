package com.example.coinwatch.domain

import com.example.coinwatch.data.remote.dto.HistoryItem
import com.example.coinwatch.data.repository.CoinRepository
import javax.inject.Inject

class GetCoinHistoryUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(coinId: String, timePeriod: String = "24h"): List<HistoryItem>? {
        return repository.getCoinHistory(coinId, timePeriod)
    }
}
