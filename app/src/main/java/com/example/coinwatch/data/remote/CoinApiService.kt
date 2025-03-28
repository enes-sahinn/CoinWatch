package com.example.coinwatch.data.remote

import com.example.coinwatch.data.remote.dto.CoinDetailResponse
import com.example.coinwatch.data.remote.dto.CoinHistoryResponse
import com.example.coinwatch.data.remote.dto.CoinListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinApiService {

    @GET("coins")
    suspend fun getCoins(): CoinListResponse

    @GET("coin/{coinId}")
    suspend fun getCoinDetail(
        @Path("coinId") coinId: String
    ): CoinDetailResponse

    @GET("coin/{coinId}/history")
    suspend fun getCoinHistory(
        @Path("coinId") coinId: String,
        @Query("timePeriod") timePeriod: String = "24h"
    ): CoinHistoryResponse
}

