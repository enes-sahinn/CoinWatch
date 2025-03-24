package com.example.coinwatch.domain

import com.example.coinwatch.data.local.CoinEntity
import com.example.coinwatch.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<List<CoinEntity>> {
        return repository.getCoins()
    }
}
