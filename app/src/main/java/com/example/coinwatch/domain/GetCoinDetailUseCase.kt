package com.example.coinwatch.domain

import com.example.coinwatch.data.remote.dto.CoinDetailDto
import com.example.coinwatch.data.repository.CoinRepository
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(coinId: String): CoinDetailDto {
        return repository.getCoinDetail(coinId)
    }
}
