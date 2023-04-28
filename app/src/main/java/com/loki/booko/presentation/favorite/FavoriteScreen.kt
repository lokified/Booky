package com.loki.booko.presentation.favorite

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.loki.booko.domain.models.BookDto
import com.loki.booko.presentation.common.AppTopBar
import com.loki.booko.presentation.common.BookItem
import com.loki.booko.presentation.navigation.Screens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel
) {

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Favorites",
                navController = navController,
                onDeleteAllClicked = {
                    viewModel.deleteAll()
                }
            )
        }
    ) {

        val state by viewModel.favoriteState.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {

            if (state.favoriteList.isNotEmpty()) {
                FavoritesSection(
                    favBooks = state.favoriteList,
                    navController = navController
                )
            }
            else {
                if (!state.isLoading) {
                    Text(
                        text = "You have no favorites",
                        color = MaterialTheme.colors.surface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }
}

@Composable
fun FavoritesSection(
    modifier: Modifier = Modifier,
    favBooks: List<BookDto>,
    navController: NavController
) {
    
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
    ) {

        items(favBooks) { book ->

            BookItem(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
                bookDto = book,
                onItemClick = {
                    navController.navigate(Screens.BookDetailScreen.navWithArgs(book.id))
                }
            )
        }
    }
}