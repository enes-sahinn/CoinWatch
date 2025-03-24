package com.example.coinwatch.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin")
data class CoinEntity(
    @PrimaryKey val uuid: String,
    val symbol: String?,
    val name: String?,
    val price: String?,
    val marketCap: String?,
    val change: String?,
    val rank: Int?,
    val volume24h: String?,
    val isFavorite: Boolean = false
)