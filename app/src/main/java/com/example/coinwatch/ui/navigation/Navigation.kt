package com.example.coinwatch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
//import com.example.coinwatch.ui.detail.DetailScreen
import com.example.coinwatch.ui.home.HomeScreen
import com.example.coinwatch.data.local.CoinEntity

@Composable
fun CoinWatchApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onCoinSelected = { coin: CoinEntity ->
                    navController.navigate("detail/${coin.uuid}")
                }
            )
        }
        composable(
            route = "detail/{coinId}",
            arguments = listOf(navArgument("coinId") { type = NavType.StringType })
        ) { backStackEntry ->
            val coinId = backStackEntry.arguments?.getString("coinId") ?: ""
           // DetailScreen(coinId = coinId)
        }
    }
}
