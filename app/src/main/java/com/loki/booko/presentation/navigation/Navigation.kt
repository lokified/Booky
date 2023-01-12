package com.loki.booko.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loki.booko.presentation.home.components.HomeScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavGraph.HomeScreen.route ) {

        composable(route = NavGraph.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
    }
}


sealed class NavGraph(val route: String) {

    object HomeScreen: NavGraph("Home_screen")
}