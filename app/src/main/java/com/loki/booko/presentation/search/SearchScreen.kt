package com.loki.booko.presentation.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.flowlayout.FlowRow
import com.loki.booko.domain.models.Term
import com.loki.booko.presentation.navigation.NavGraph

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    onTermSearched: (String) -> Unit
) {

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

        var term by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current

        SearchTextFieldSection(
            onClick = {
                if (term.isNotEmpty()) {
                    navController.popBackStack()
                    onTermSearched(term)
                    keyboardController?.hide()
                }
            },
            enteredTerm = { term = it },
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Search History",
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        TermsSection(

            modifier = Modifier.padding(16.dp),
            terms = viewModel.searchTermState.value.searchTermList,
            onItemClick = {
                          term = it
            },
            onCancelClick = {
                viewModel.deleteSearchTerm(it)
            }
        )
    }
}


@Composable
fun SearchTextFieldSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enteredTerm: (String) -> Unit
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
            var text by remember {
                mutableStateOf("")
            }
            
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    enteredTerm(it)
                                },
                placeholder = {
                    Text(
                        text = "Enter keywords eg. romance",
                        color = MaterialTheme.colors.secondary
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colors.surface,
                    unfocusedIndicatorColor = MaterialTheme.colors.surface
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
                            tint = MaterialTheme.colors.surface
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
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onItemClick(term.searchTerm) },
        contentAlignment = Alignment.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {

            Text(
                text = term.searchTerm,
                color = MaterialTheme.colors.secondary,
                maxLines = 1,
                fontSize = 18.sp
            )
            
            Spacer(modifier = Modifier.width(2.dp))

            IconButton(
                onClick = { onCancelClick(term) }
            ) {

                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "Cancel",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}