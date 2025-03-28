package com.example.coinwatch.data.remote.dto

data class CoinDetailResponse(
    val data: CoinDetailData
)

data class CoinDetailData(
    val coin: CoinDetailDto
)

data class CoinDetailDto(
    val uuid: String?,
    val symbol: String?,
    val name: String?,
    val description: String?,
    val color: String?,
    val iconUrl: String?,
    val websiteUrl: String?,
    val rank: Int?,
    val price: String?,
    val change: String?,
    val marketCap: String?,
    val volume24h: String?,
    val allTimeHigh: PriceData?,
    val supply: SupplyData?
)

data class PriceData(
    val price: String?,
    val timestamp: Long?
)

data class SupplyData(
    val total: String?,
    val circulating: String?
)
