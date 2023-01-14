package com.loki.booko.presentation.favorite

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.loki.booko.presentation.common.AppTopBar

@Composable
fun FavoriteScreen(
    navController: NavController
) {

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Favorites",
                navController = navController
            )
        }
    ) {


    }
}