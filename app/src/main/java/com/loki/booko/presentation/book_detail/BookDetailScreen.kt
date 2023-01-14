package com.loki.booko.presentation.book_detail

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.loki.booko.presentation.common.AppTopBar

@Composable
fun BookDetailScreen(
    navController: NavController
) {

    Scaffold (
        topBar = {
            AppTopBar(
                title = "Book Details",
                navController = navController
            )
        }
    ){


    }

}