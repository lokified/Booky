package com.loki.booko.presentation.book_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.loki.booko.domain.models.Favorite
import com.loki.booko.presentation.MainActivity
import com.loki.booko.presentation.common.AppTopBar
import com.loki.booko.util.extensions.getActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookDetailScreen(
    navController: NavController,
    viewModel: BookDetailViewModel
) {

    val state = viewModel.bookDetailState.value

    val favoriteBookState = viewModel.favoriteBook.collectAsState()

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {

        viewModel.favoriteBook.collectLatest { state ->

            if (state.isLoading) {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Saving",
                    duration = SnackbarDuration.Long
                )
            }

            if (state.message.isNotEmpty()) {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = favoriteBookState.value.message,
                    duration = SnackbarDuration.Long
                )
            }
        }
    }


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

            state.favorite?.let {
                BottomSection(
                    favorite = it,
                    onDownloadClick = {
                        val message = viewModel.downloadBook(
                            favorite = it,
                            activity = (context.getActivity() as MainActivity)
                        )

                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = message
                            )
                        }
                    }
                )
            }
        },
        scaffoldState = scaffoldState
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

                    state.favorite?.let { book ->
                        TopSection(
                            modifier = Modifier.padding(16.dp),
                            favorite = book
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

                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {

                        viewModel.getBookIsRead(state.favorite!!)

                        val isRead = viewModel.isRead.value

                        val favText = if (isRead) "Favorite" else "Save to Favorite"

                        TextButton(
                            onClick = {
                                if (!isRead) {
                                    viewModel.saveAsFavorite(state.favorite)
                                }
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favorite"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = favText)
                        }

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
    favorite: Favorite
) {

    Box(modifier = modifier) {
        Row(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            val painter = rememberImagePainter(
                data = favorite.formats.imagejpeg
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
                    text = favorite.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.surface,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                val authors = favorite.author?.map { it.name }?.joinToString(separator = ",")
                Text(
                    text = "Author(s) | $authors",
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic,
                    maxLines = 2
                )

                val languages = favorite.languages.joinToString(separator = ",")

                Text(
                    text = "Language(s) | $languages",
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
    favorite: Favorite,
    onDownloadClick: () -> Unit
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
                text = "${favorite.download_count} Downloads",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { onDownloadClick() }
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