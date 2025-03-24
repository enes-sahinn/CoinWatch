package com.example.coinwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.coinwatch.ui.navigation.CoinWatchApp
import com.example.coinwatch.ui.theme.CoinWatchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinWatchTheme {
                CoinWatchApp()
            }
        }
    }
}
