package com.loki.booko.presentation.book_detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.loki.booko.domain.models.Favorite
import com.loki.booko.domain.network_service.NetworkStatus
import com.loki.booko.presentation.MainActivity
import com.loki.booko.presentation.common.AppTopBar
import com.loki.booko.util.TextUtils
import com.loki.booko.util.extensions.getActivity
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BookDetailScreen(
    navController: NavController,
    viewModel: BookDetailViewModel,
) {

    val bookState by viewModel.bookDetailState.collectAsStateWithLifecycle()
    val favoriteBookState by viewModel.favoriteBook.collectAsStateWithLifecycle()
    val networkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = SnackbarHostState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = favoriteBookState.isLoading) {
        if (favoriteBookState.isLoading) {
            snackbarHostState.showSnackbar(
                message = "Saving",
            )
        }
    }

    LaunchedEffect(key1 = favoriteBookState.message ) {
        if (favoriteBookState.message.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                message = favoriteBookState.message,
            )
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
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {

            bookState.favorite?.let {
                BottomSection(
                    favorite = it,
                    onDownloadClick = {

                        if (networkStatus == NetworkStatus.Connected) {
                            viewModel.downloadBook(
                                favorite = it,
                                activity = (context.getActivity() as MainActivity)
                            )

                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = viewModel.downloadMessage.value
                                )
                            }
                        }

                        if (networkStatus == NetworkStatus.Disconnected) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "No network connection"
                                )
                            }
                        }
                    }
                )
            }
        },
    ){ padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            if (bookState.isLoading) {
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

                    bookState.favorite?.let { book ->
                        TopSection(
                            modifier = Modifier.padding(16.dp),
                            favorite = book
                        )

                        MidSection(
                            bookItem = book,
                            isFavorite = viewModel.isFavorite.value,
                            onFavoriteClick = {
                                if (!viewModel.isFavorite.value) {
                                    viewModel.saveAsFavorite(book)
                                }
                                if (viewModel.isFavorite.value) {
                                    viewModel.removeAsFavorite(book)
                                }
                            },
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    bookState.bookSynopsis?.let { synopsis ->

                        Text(
                            text = "Synopsis",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp),
                        )

                        Text(
                            text = synopsis,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                }
            }

            if (bookState.errorMessage.isNotBlank()) {
                Text(
                    text = bookState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
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

            AsyncImage(
                model = favorite.formats.imagejpeg,
                contentDescription = "Book_Image",
                modifier = Modifier.size(
                    width = 200.dp,
                    height = 200.dp
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = favorite.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 2
            )
        }
    }
}


@Composable
fun MidSection(
    modifier: Modifier = Modifier,
    bookItem: Favorite,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {

    val authors = TextUtils.getAuthorsAsString(bookItem.author!!)
    val languages = TextUtils.getLanguagesAsString(bookItem.languages)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(4.dp)
            )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(8.dp)
        ) {

            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Language")
                Text(text = languages)
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Authors")
                Text(text = authors!!)
            }
            Spacer(modifier = Modifier.weight(1f))

            Box(contentAlignment = Alignment.Center) {
                IconButton(
                    onClick = {
                        onFavoriteClick()
                    }
                ) {
                    Icon(
                        imageVector = if (!isFavorite) Icons.Outlined.FavoriteBorder else Icons.Default.Favorite,
                        contentDescription = null
                    )
                }
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

    Box(
        modifier = modifier
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "${favorite.download_count} Downloads",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { onDownloadClick() }
            ) {

                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = "Download",
                    modifier = modifier
                        .padding(4.dp)
                )
            }
        }
    }

}