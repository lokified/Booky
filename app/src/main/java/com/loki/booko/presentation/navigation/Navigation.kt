package com.loki.booko.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.loki.booko.presentation.BookyAppState
import com.loki.booko.presentation.book_detail.BookDetailScreen
import com.loki.booko.presentation.book_detail.BookDetailViewModel
import com.loki.booko.presentation.favorite.FavoriteScreen
import com.loki.booko.presentation.favorite.FavoriteViewModel
import com.loki.booko.presentation.home.HomeViewModel
import com.loki.booko.presentation.home.components.HomeScreen
import com.loki.booko.presentation.search.SearchScreen
import com.loki.booko.presentation.search.SearchViewModel
import com.loki.booko.presentation.settings.SettingsScreen
import com.loki.booko.util.Constants.BOOK_ID


@ExperimentalAnimationApi
@Composable
fun Navigation(
    appState: BookyAppState
) {

    AnimatedNavHost(
        navController = appState.navController,
        startDestination = Screens.HomeScreen.route,
    ) {

        composable(
            route = Screens.SearchScreen.route,
            enterTransition = {
                if (initialState.destination.route == Screens.HomeScreen.route)
                    slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(800)
                    ) else null
            },
            popExitTransition = {
                if (targetState.destination.route == Screens.HomeScreen.route)
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(800)

                    ) else null
            }
        ) {
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(
                viewModel = viewModel,
                openScreen = { appState.navigate(it) }
            )
        }

        composable(
            route = Screens.HomeScreen.route,
            enterTransition = {
                if (initialState.destination.route == Screens.BookDetailScreen.route)
                    slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(800)
                    )
                else if (initialState.destination.route == Screens.SearchScreen.route)
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(800)
                ) else null
            },
            popExitTransition = {
                if (targetState.destination.route == Screens.BookDetailScreen.route)
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(800)
                    )

                else if (targetState.destination.route == Screens.SearchScreen.route)
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(800)
                    ) else null
            }
        ) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val books = homeViewModel.bookPagingFlow.collectAsLazyPagingItems()
            HomeScreen(
                navController = appState.navController,
                viewModel = homeViewModel,
                books = books
            )
        }

        composable(
            route = Screens.FavoriteScreen.route
        ) {

            val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
            FavoriteScreen(
                navController = appState.navController,
                viewModel = favoriteViewModel
            )
        }

        composable(
            route = Screens.SettingsScreen.route
        ) {
            SettingsScreen(navController = appState.navController)
        }

        composable(
            route = Screens.BookDetailScreen.withBookId(),
            arguments = listOf(
                navArgument(name = BOOK_ID) {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                if (initialState.destination.route == Screens.HomeScreen.route)
                    slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(800)
                    ) else null
            },
            exitTransition = {
                if (targetState.destination.route == Screens.HomeScreen.route)
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(800)
                    ) else null
            }
        ) {

            val bookDetailViewModel = hiltViewModel<BookDetailViewModel>()
            BookDetailScreen(
                navController = appState.navController,
                viewModel = bookDetailViewModel
            )
        }

    }
}


sealed class Screens(val route: String) {

    object HomeScreen: Screens("Home_screen")
    object FavoriteScreen: Screens("Favorite_screen")
    object SettingsScreen: Screens("Settings_screen")
    object SearchScreen: Screens("Search_screen")
    object BookDetailScreen: Screens("Book_detail_screen")

    fun withBookId(): String {
        return "${BookDetailScreen.route}/{$BOOK_ID}"
    }

    fun navWithArgs(bookId: Int): String {
        return "${BookDetailScreen.route}/$bookId"
    }
}