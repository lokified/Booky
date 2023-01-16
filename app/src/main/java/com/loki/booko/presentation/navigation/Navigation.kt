package com.loki.booko.presentation.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.loki.booko.presentation.book_detail.BookDetailScreen
import com.loki.booko.presentation.favorite.FavoriteScreen
import com.loki.booko.presentation.home.components.HomeScreen
import com.loki.booko.presentation.search.SearchScreen
import com.loki.booko.presentation.settings.SettingsScreen
import com.loki.booko.util.Constants.BOOK_ID

@Composable
fun Navigation(
    navController: NavHostController
) {


    NavHost(navController = navController, startDestination = NavGraph.HomeScreen.route ) {

        var term = ""

        composable(route = NavGraph.SearchScreen.route) {
            SearchScreen(
                navController = navController
            ) {
                term = it
            }
        }

        composable(route = NavGraph.HomeScreen.route) {
            HomeScreen(navController = navController, searchTerm = term)
        }

        composable(route = NavGraph.FavoriteScreen.route) {
            FavoriteScreen(navController = navController)
        }

        composable(route = NavGraph.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }

        composable(
            route = NavGraph.BookDetailScreen.withBookId(),
            arguments = listOf(
                navArgument(name = BOOK_ID) {
                    type = NavType.IntType
                }
            )
        ) {

            BookDetailScreen(navController = navController)

        }

    }
}


sealed class NavGraph(val route: String) {

    object HomeScreen: NavGraph("Home_screen")
    object FavoriteScreen: NavGraph("Favorite_screen")
    object SettingsScreen: NavGraph("Settings_screen")
    object SearchScreen: NavGraph("Search_screen")
    object BookDetailScreen: NavGraph("Book_detail_screen")

    fun withBookId(): String {
        return "${BookDetailScreen.route}/{$BOOK_ID}"
    }

    fun navWithArgs(bookId: Int): String {
        return "${BookDetailScreen.route}/$bookId"
    }
}