package com.loki.booko.presentation.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.loki.booko.domain.models.BookItem
import com.loki.booko.domain.network_service.NetworkStatus
import com.loki.booko.presentation.common.AppTopBar
import com.loki.booko.presentation.common.BookItem
import com.loki.booko.presentation.navigation.Screens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel,
    snackbarHostState: SnackbarHostState = SnackbarHostState()
) {

    val context = LocalContext.current
    val networkStatus = viewModel.networkStatus.collectAsStateWithLifecycle()

    if (networkStatus.value == NetworkStatus.Disconnected) {
        LaunchedEffect(key1 =networkStatus) {
            snackbarHostState.showSnackbar("You are offline")
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Favorites",
                navController = navController,
                onDeleteAllClicked = {
                    viewModel.deleteAll()
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->

        val state by viewModel.favoriteState.collectAsState()

        Box(modifier = Modifier.fillMaxSize().padding(padding)) {

            if (state.favoriteList.isNotEmpty()) {
                FavoritesSection(
                    favBooks = state.favoriteList,
                    navController = navController,
                    context = context,
                    networkStatus = networkStatus.value
                )
            }
            else {
                if (!state.isLoading) {
                    Text(
                        text = "You have no favorites",
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
    favBooks: List<BookItem>,
    navController: NavController,
    context: Context,
    networkStatus: NetworkStatus
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
                book = book,
                onItemClick = {
                    if (networkStatus == NetworkStatus.Disconnected) {
                        Toast.makeText(
                            context,
                            "You are offline",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if (networkStatus == NetworkStatus.Connected) {
                        navController.navigate(
                            Screens.BookDetailScreen.navWithArgs(
                                book.id
                            )
                        )
                    }
                }
            )
        }
    }
}