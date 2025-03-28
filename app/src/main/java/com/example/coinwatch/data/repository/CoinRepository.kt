package com.example.coinwatch.data.repository

import com.example.coinwatch.data.local.CoinDao
import com.example.coinwatch.data.local.CoinEntity
import com.example.coinwatch.data.remote.CoinApiService
import com.example.coinwatch.data.remote.dto.CoinDetailDto
import com.example.coinwatch.data.remote.dto.HistoryItem
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class CoinRepository @Inject constructor(
    private val coinApiService: CoinApiService,
    private val coinDao: CoinDao
) {
    suspend fun fetchCoins() {
        val response = coinApiService.getCoins()
        val coinEntities = response.data.coins.map { coinDto ->
            CoinEntity(
                uuid = coinDto.uuid,
                symbol = coinDto.symbol,
                name = coinDto.name,
                price = coinDto.price,
                marketCap = coinDto.marketCap,
                change = coinDto.change,
                rank = coinDto.rank,
                volume24h = coinDto.volume24h,
                isFavorite = false
            )
        }
        coinDao.clearCoins()
        coinDao.insertCoins(coinEntities)
    }

    fun getCoins(): Flow<List<CoinEntity>> {
        return coinDao.getAllCoins()
    }

    suspend fun updateFavoriteStatus(coinId: String, isFavorite: Boolean) {
        coinDao.updateFavoriteStatus(coinId, isFavorite)
    }

    suspend fun getCoinDetail(coinId: String): CoinDetailDto {
        val response = coinApiService.getCoinDetail(coinId)
        return response.data.coin
    }

    suspend fun getCoinHistory(coinId: String, timePeriod: String = "24h"): List<HistoryItem>? {
        val response = coinApiService.getCoinHistory(coinId, timePeriod)
        return response.data.history
    }
}
