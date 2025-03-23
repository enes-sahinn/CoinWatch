package com.example.coinwatch.data.remote.dto

data class CoinListResponse(
    val data: CoinData
)

data class CoinData(
    val coins: List<CoinDto>
)

data class CoinDto(
    val uuid: String,
    val symbol: String,
    val name: String,
    val price: String,
    val marketCap: String,
    val change: String,
    val rank: Int,
    val volume24h: String
)
