package com.example.coinwatch.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.coinwatch.domain.FetchCoinsUseCase
import com.example.coinwatch.domain.GetCoinsUseCase
import com.example.coinwatch.domain.UpdateFavoriteStatusUseCase
import com.example.coinwatch.data.local.CoinEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchCoinsUseCase: FetchCoinsUseCase,
    private val getCoinsUseCase: GetCoinsUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : ViewModel() {

    val coins = getCoinsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        refreshCoins()
    }

    fun refreshCoins() {
        viewModelScope.launch {
            fetchCoinsUseCase()
        }
    }

    fun onFavoriteClick(coinId: String, currentIsFavorite: Boolean) {
        viewModelScope.launch {
            updateFavoriteStatusUseCase(coinId, !currentIsFavorite)
        }
    }
}
