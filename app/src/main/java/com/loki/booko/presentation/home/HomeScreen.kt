package com.loki.booko.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.loki.booko.domain.models.BookItem
import com.loki.booko.domain.network_service.NetworkStatus
import com.loki.booko.presentation.common.BookItem
import com.loki.booko.presentation.common.AppTopBar
import com.loki.booko.presentation.navigation.Screens

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    books: LazyPagingItems<BookItem>,
    snackbarHostState: SnackbarHostState = SnackbarHostState()
) {
    val context = LocalContext.current
    val networkStatus = viewModel.networkStatus.collectAsStateWithLifecycle()

    if (networkStatus.value == NetworkStatus.Disconnected) {
        LaunchedEffect(key1 =networkStatus) {
            snackbarHostState.showSnackbar("You are offline")
        }
    }

    LaunchedEffect(key1 = books.loadState) {

        if (books.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (books.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "All Books",
                navController = navController
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {


            if (books.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else {
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 50.dp)
                ) {

                    items(books) { book ->

                        book?.let {
                            BookItem(
                                book = it,
                                modifier = Modifier.padding(horizontal = 16.dp,  vertical = 12.dp),
                                onItemClick = {
                                    if (networkStatus.value == NetworkStatus.Disconnected) {
                                        Toast.makeText(
                                            context,
                                            "You are offline",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                    if (networkStatus.value == NetworkStatus.Connected) {
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

                    item {
                        if (books.loadState.append is LoadState.Loading) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorSection(
    modifier: Modifier = Modifier,
    message: String,
    color: Color = MaterialTheme.colorScheme.error,
) {
    Text(
        text = message,
        color = color,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    )
}

@Composable
fun BookSection(
    books: List<BookItem>,
    navController: NavController
) {

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
    ) {

        items(books) { book ->

            BookItem(
                book = book,
                modifier = Modifier.padding(horizontal = 16.dp,  vertical = 12.dp),
                onItemClick = {
                    navController.navigate(Screens.BookDetailScreen.navWithArgs(book.id))
                }
            )
        }
    }
}