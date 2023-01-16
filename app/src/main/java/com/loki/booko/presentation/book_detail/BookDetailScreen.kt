package com.loki.booko.presentation.book_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.loki.booko.domain.models.BookDto
import com.loki.booko.presentation.common.AppTopBar

@Composable
fun BookDetailScreen(
    navController: NavController,
    viewModel: BookDetailViewModel = hiltViewModel()
) {

    val state = viewModel.bookDetailState.value

    Scaffold (
        topBar = {
            AppTopBar(
                title = "Book Details",
                navController = navController,
                onFavoriteClicked = {

                }
            )
        },
        bottomBar = {

            state.book?.let {
                BottomSection(bookDto = it)
            }
        }
    ){

        Box(modifier = Modifier.fillMaxSize()) {

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else {

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(bottom = 80.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    state.book?.let { book ->
                        TopSection(
                            modifier = Modifier.padding(16.dp),
                            bookDto = book
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Synopsis",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp),
                    )

                    state.bookSynopsis?.let { synopsis ->
                        Text(
                            text = synopsis,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                }
            }

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
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    bookDto: BookDto
) {

    Box(modifier = modifier) {
        Row(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            val painter = rememberImagePainter(
                data = bookDto.formats.imagejpeg
            )

            Image(
                painter = painter,
                contentDescription = "Book_Image",
                modifier = Modifier.size(
                    width = 200.dp,
                    height = 200.dp
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = bookDto.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.surface,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                val authors = bookDto.author?.map { it.name }?.joinToString(separator = ",")
                Text(
                    text = "Author(s): $authors",
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic,
                    maxLines = 2
                )

                val languages = bookDto.languages.joinToString(separator = ",")

                Text(
                    text = "Language(s): $languages",
                    fontSize = 16.sp,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun BottomSection(
    modifier: Modifier = Modifier,
    bookDto: BookDto
) {

    Box(modifier = modifier
        .background(
            color = MaterialTheme.colors.surface
        )
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "${bookDto.download_count} Downloads",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {  }
            ) {

                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = "Download",
                    modifier = modifier
                        .padding(4.dp),
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    }

}