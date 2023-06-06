package com.loki.booko.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.flowlayout.FlowRow
import com.loki.booko.domain.models.BookItem
import com.loki.booko.domain.models.Term
import com.loki.booko.presentation.common.BookItem
import com.loki.booko.presentation.navigation.Screens

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    openScreen: (String) -> Unit
) {

    var term by remember { viewModel.searchTerm }
    var isSearching by remember { viewModel.isSearching }
    val keyboardController = LocalSoftwareKeyboardController.current
    val state by viewModel.searchState.collectAsStateWithLifecycle()

    Column {

        SearchTextFieldSection(
            onClick = {
                if (term.isNotEmpty()) {
                    viewModel.saveSearchTerm(term.trim())
                    viewModel.searchBook(term.trim())
                    isSearching = true
                    keyboardController?.hide()
                }
            },
            onTermChange = {
                term = it

                if (it.isEmpty()) {
                    isSearching = false
                    viewModel.initializeScreen()
                } },
            modifier = Modifier.padding(top = 16.dp),
            term = term
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = if(!isSearching) "Search History" else "Search Results",
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        AnimatedVisibility(
            visible = !isSearching,
        ) {

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {


                Spacer(modifier = Modifier.height(8.dp))

                TermsSection(

                    modifier = Modifier.padding(16.dp),
                    terms = state.searchTermList,
                    onItemClick = {
                        term = it
                    },
                    onCancelClick = {
                        viewModel.deleteSearchTerm(it)
                    }
                )
            }
        }

        AnimatedVisibility(
            visible = isSearching
        ) {

            Box(modifier = Modifier.fillMaxSize()) {

                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                if (state.bookList.isNotEmpty()) {

                    SearchedSection(
                        modifier = Modifier.fillMaxSize(),
                        books = state.bookList,
                        onItemClick = { openScreen(Screens.BookDetailScreen.navWithArgs(it.id)) }
                    )
                }

                if (state.errorMessage.isNotBlank()) {
                    Text(
                        text = state.errorMessage,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@Composable
fun SearchTextFieldSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onTermChange: (String) -> Unit,
    term: String
) {
    
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            
            TextField(
                value = term,
                onValueChange = { onTermChange(it) },
                placeholder = {
                    Text(
                        text = "Enter keywords eg. romance"
                    )
                },
                colors = TextFieldDefaults.colors(

                ),
                trailingIcon = {
                    IconButton(
                        onClick = { onClick()  }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search",
                            modifier = Modifier
                                .padding(2.dp)
                                .size(35.dp),
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun TermsSection(
    modifier: Modifier = Modifier,
    terms: List<Term>,
    onItemClick: (String) -> Unit,
    onCancelClick: (Term) -> Unit
) {

    FlowRow(
        mainAxisSpacing = 5.dp,
        crossAxisSpacing = 10.dp,
        modifier = modifier.fillMaxWidth()
    ) {

        terms.forEach { term ->
            Term(
                term = term,
                onItemClick = { onItemClick(term.searchTerm) },
                onCancelClick = { onCancelClick(term)  }
            )
        }
    }
}

@Composable
fun Term(
    modifier: Modifier = Modifier,
    term: Term,
    onItemClick: (String) -> Unit,
    onCancelClick: (Term) -> Unit
) {

    AssistChip(
        onClick = { onItemClick(term.searchTerm) },
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        trailingIcon = {
            IconButton(
                onClick = { onCancelClick(term) }
            ) {

                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "Cancel",
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        label = {
            Text(
                text = term.searchTerm,
                fontSize = 18.sp
            )
        }
    )
}

@Composable
fun SearchedSection(
    modifier: Modifier = Modifier,
    books: List<BookItem>,
    onItemClick: (BookItem) -> Unit
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp)
    ) {

        items(books) { book ->
            BookItem(
                modifier = Modifier.padding(8.dp),
                book = book,
                onItemClick = { onItemClick(book) }
            )
        }
    }
}