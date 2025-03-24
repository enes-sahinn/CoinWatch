package com.example.coinwatch.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin")
    fun getAllCoins(): Flow<List<CoinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coins: List<CoinEntity>): List<Long>

    @Query("DELETE FROM coin")
    suspend fun clearCoins(): Int

    @Query("UPDATE coin SET isFavorite = :isFavorite WHERE uuid = :coinId")
    suspend fun updateFavoriteStatus(coinId: String, isFavorite: Boolean)
}
