package com.example.coinwatch.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coinwatch.data.remote.dto.CoinDetailDto
import com.example.coinwatch.data.remote.dto.HistoryItem
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    coinId: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val coinDetail by viewModel.coinDetail.collectAsState()
    val historyItems by viewModel.historyItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(key1 = coinId) {
        viewModel.loadCoinDetail(coinId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Coin Detail") }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                coinDetail?.let { detail ->
                    DetailContent(
                        coinDetail = detail,
                        historyItems = historyItems,
                        onFavoriteClick = { viewModel.toggleFavorite() }
                    )
                } ?: Text(
                    text = "Coin detail not available",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun DetailContent(
    coinDetail: CoinDetailDto,
    historyItems: List<HistoryItem>,
    onFavoriteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll( rememberScrollState())
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = coinDetail.name ?: "Unknown Coin",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Rank: ${coinDetail.rank ?: "-"}")

                val formattedPrice = coinDetail.price?.toDoubleOrNull()?.let {
                    String.format("%.2f", it)
                } ?: "-"
                Text(text = "Price: $$formattedPrice")

                val change = coinDetail.change ?: "-"
                Text(text = "Change: $change%")

                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onFavoriteClick) {
                    Text("Toggle favorite coin")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Coin Chart")

        Spacer(modifier = Modifier.height(16.dp))

        ChartCard(historyItems = historyItems)
    }
}

@Composable
fun ChartCard(historyItems: List<HistoryItem>) {

    Card(
        modifier = Modifier.fillMaxSize().height(300.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),

            factory = { context ->
                LineChart(context).apply {

                    description.isEnabled = false
                    legend.isEnabled = false
                    xAxis.setDrawGridLines(false)
                    axisLeft.setDrawGridLines(false)
                    axisRight.setDrawGridLines(false)
                    setNoDataText("No chart data available")

                    setPinchZoom(true)
                    setDragEnabled(true)
                    setScaleEnabled(true)
                }
            },
            update = { lineChart ->
                val entries = historyItems
                    .sortedBy { it.timestamp }
                    .mapIndexed { index, item ->
                        val yValue = item.price?.toFloatOrNull() ?: 0f
                        Entry(index.toFloat(), yValue)
                    }

                val dataSet = LineDataSet(entries, "Price History").apply {
                    color = android.graphics.Color.BLUE
                    lineWidth = 2f
                    setDrawCircles(false)
                    setDrawValues(false)
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                }

                lineChart.data = LineData(dataSet)
                lineChart.invalidate()
            }
        )
    }
}