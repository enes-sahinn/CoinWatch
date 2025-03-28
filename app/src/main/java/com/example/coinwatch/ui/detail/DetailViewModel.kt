package com.example.coinwatch.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinwatch.data.remote.dto.CoinDetailDto
import com.example.coinwatch.data.remote.dto.HistoryItem
import com.example.coinwatch.domain.GetCoinDetailUseCase
import com.example.coinwatch.domain.GetCoinHistoryUseCase
import com.example.coinwatch.domain.UpdateFavoriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    private val getCoinHistoryUseCase: GetCoinHistoryUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : ViewModel() {

    private val _coinDetail = MutableStateFlow<CoinDetailDto?>(null)
    val coinDetail = _coinDetail.asStateFlow()

    private val _historyItems = MutableStateFlow<List<HistoryItem>>(emptyList())
    val historyItems = _historyItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun loadCoinDetail(coinId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val detail = getCoinDetailUseCase(coinId)
                _coinDetail.value = detail

                val history = getCoinHistoryUseCase(coinId, "24h")
                if (history != null) {
                    _historyItems.value = history
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentUuid = coinDetail.value?.uuid ?: return@launch
            updateFavoriteStatusUseCase(currentUuid, true)
        }
    }
}
