package com.example.coinwatch.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.coinwatch.domain.FetchCoinsUseCase
import com.example.coinwatch.domain.GetCoinsUseCase
import com.example.coinwatch.domain.UpdateFavoriteStatusUseCase
import com.example.coinwatch.data.local.CoinEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchCoinsUseCase: FetchCoinsUseCase,
    private val getCoinsUseCase: GetCoinsUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : ViewModel() {

    private val coinFlow = getCoinsUseCase()

    private val _sortType = MutableStateFlow(SortType.PRICE)
    val sortType: StateFlow<SortType> = _sortType.asStateFlow()

    val coins: StateFlow<List<CoinEntity>> = combine(coinFlow, sortType) { coinList, sort ->
        when (sort) {
            SortType.PRICE -> coinList.sortedBy { it.price?.toDoubleOrNull() ?: 0.0 }
            SortType.MARKET_CAP -> coinList.sortedBy { it.marketCap?.toDoubleOrNull() ?: 0.0 }
            SortType.VOLUME24H -> coinList.sortedBy { it.volume24h?.toDoubleOrNull() ?: 0.0 }
            SortType.CHANGE -> coinList.sortedBy { it.change?.toDoubleOrNull() ?: 0.0 }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

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

    fun onSortTypeSelected(newSort: SortType) {
        _sortType.value = newSort
    }
}
