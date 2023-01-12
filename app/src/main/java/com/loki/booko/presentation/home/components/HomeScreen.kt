package com.loki.booko.presentation.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loki.booko.domain.models.BookDto
import com.loki.booko.presentation.common.BookItem
import com.loki.booko.presentation.common.TopBar
import com.loki.booko.presentation.home.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            TopBar(title = "All Books")
        }
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            val state = viewModel.bookState.value

            BookSection(books = state.bookList)

            if (state.errorMessage.isNotBlank()) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
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
fun BookSection(
    books: List<BookDto>
) {

    LazyColumn (modifier = Modifier.fillMaxSize()) {

        items(books) { book ->

            BookItem(
                bookDto = book,
                modifier = Modifier.padding(horizontal = 16.dp,  vertical = 12.dp)
            )
        }
    }
}