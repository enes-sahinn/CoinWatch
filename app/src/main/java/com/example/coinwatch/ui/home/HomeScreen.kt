package com.example.coinwatch.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coinwatch.data.local.CoinEntity
import com.example.coinwatch.R // ic_star_filled ve ic_star_outline ikonları burada

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onCoinSelected: (CoinEntity) -> Unit
) {
    val coins by viewModel.coins.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Coin Watch") })
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(coins) { coin ->
                CoinRow(
                    coin = coin,
                    onClick = { onCoinSelected(coin) },
                    onFavoriteClick = { coinId, currentFav ->
                        viewModel.onFavoriteClick(coinId, currentFav)
                    }
                )
            }
        }
    }
}

@Composable
fun CoinRow(
    coin: CoinEntity,
    onClick: () -> Unit,
    onFavoriteClick: (String, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = "${coin.rank}. ${coin.name} (${coin.symbol})")
            Text(text = "Price: ${coin.price}")
        }

        // FAVORİ BUTONU (YILDIZ İKONU)
        IconButton(
            onClick = { onFavoriteClick(coin.uuid, coin.isFavorite) }
        ) {
            val iconRes = if (coin.isFavorite) {
                R.drawable.ic_star_filled
            } else {
                R.drawable.ic_star_outline
            }
            Icon(painter = painterResource(id = iconRes), contentDescription = null)
        }
    }
}
