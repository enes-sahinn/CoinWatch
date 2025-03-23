package com.example.coinwatch.data.remote

import com.example.coinwatch.data.remote.dto.CoinListResponse
import retrofit2.http.GET

interface CoinApiService {
    @GET("coins")
    suspend fun getCoins(): CoinListResponse
}
