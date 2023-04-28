package com.loki.booko.presentation.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.loki.booko.domain.models.BookDto
import com.loki.booko.presentation.common.BookItem
import com.loki.booko.presentation.common.AppTopBar
import com.loki.booko.presentation.home.HomeViewModel
import com.loki.booko.presentation.navigation.Screens

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    searchTerm: String = ""
) {

    LaunchedEffect(key1 = true) {
        if (searchTerm.isNotEmpty()) {
            viewModel.searchBook(searchTerm)
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "All Books",
                navController = navController
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            val state = viewModel.bookState.collectAsState()

            if (state.value.bookList.isNotEmpty()) {
                BookSection(
                    books = state.value.bookList,
                    navController = navController
                )
            }

            if (state.value.errorMessage.isNotBlank()) {
                ErrorSection(
                    message = state.value.errorMessage,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (state.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun ErrorSection(
    modifier: Modifier = Modifier,
    message: String,
    color: Color = MaterialTheme.colors.error,
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
    books: List<BookDto>,
    navController: NavController
) {

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
    ) {

        items(books) { book ->

            BookItem(
                bookDto = book,
                modifier = Modifier.padding(horizontal = 16.dp,  vertical = 12.dp),
                onItemClick = {
                    navController.navigate(Screens.BookDetailScreen.navWithArgs(book.id))
                }
            )
        }
    }
}