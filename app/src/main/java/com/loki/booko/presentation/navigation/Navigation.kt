package com.loki.booko.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loki.booko.presentation.favorite.FavoriteScreen
import com.loki.booko.presentation.home.components.HomeScreen
import com.loki.booko.presentation.search.SearchScreen
import com.loki.booko.presentation.settings.SettingsScreen

@Composable
fun Navigation(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = NavGraph.HomeScreen.route ) {

        composable(route = NavGraph.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(route = NavGraph.FavoriteScreen.route) {
            FavoriteScreen()
        }

        composable(route = NavGraph.SettingsScreen.route) {
            SettingsScreen()
        }

        composable(route = NavGraph.SearchScreen.route) {
            SearchScreen()
        }
    }
}


sealed class NavGraph(val route: String) {

    object HomeScreen: NavGraph("Home_screen")
    object FavoriteScreen: NavGraph("Favorite_screen")
    object SettingsScreen: NavGraph("Settings_screen")
    object SearchScreen: NavGraph("Search_screen")
}