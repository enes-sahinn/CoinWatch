package com.example.coinwatch.data.remote.dto

data class CoinHistoryResponse(
    val data: HistoryData
)

data class HistoryData(
    val change: String?,
    val history: List<HistoryItem>?
)

data class HistoryItem(
    val price: String?,
    val timestamp: Long?
)
