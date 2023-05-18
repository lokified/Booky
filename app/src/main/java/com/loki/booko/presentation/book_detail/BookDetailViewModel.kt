package com.loki.booko.presentation.book_detail

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.booko.domain.models.Favorite
import com.loki.booko.domain.repository.local.FavoriteBookRepository
import com.loki.booko.domain.repository.remote.GoogleBookRepository
import com.loki.booko.domain.use_cases.books.BookUseCase
import com.loki.booko.presentation.MainActivity
import com.loki.booko.util.Constants.BOOK_ID
import com.loki.booko.util.Constants.DOWNLOAD_DIR
import com.loki.booko.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookUseCase: BookUseCase,
    savedStateHandle: SavedStateHandle,
    private val googleBookRepository: GoogleBookRepository,
    private val favoriteBookRepository: FavoriteBookRepository
): ViewModel() {

    private val _bookDetailState = mutableStateOf(BookDetailState())
    val bookDetailState: State<BookDetailState> = _bookDetailState

    private val _favoriteBook = MutableStateFlow(FavoriteBookState())
    val favoriteBook = _favoriteBook.asStateFlow()

    val isRead = mutableStateOf(false)

    init {
        savedStateHandle.get<Int>(BOOK_ID)?.let { bookId ->
            getBookDetail(bookId)
        }
    }

    private fun getBookDetail(bookId: Int) {

        bookUseCase.getBookDetail(bookId).onEach {  result ->

            when(result) {

                is Resource.Loading -> {
                    _bookDetailState.value = BookDetailState(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    delay(1000L)
                    val bookInfoList = googleBookRepository.getBookInfo(result.data?.title!!)

                    if(bookInfoList?.isEmpty() == true) {
                        _bookDetailState.value = BookDetailState(
                            errorMessage = "Could not find the book detail"
                        )
                    }
                    _bookDetailState.value = BookDetailState(
                        favorite = result.data,
                        bookSynopsis = bookInfoList?.get(0)?.volumeInfo?.description
                    )

                }

                is Resource.Error -> {
                    _bookDetailState.value = BookDetailState(
                        errorMessage = result.message ?: "Something went wrong"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveAsFavorite(favorite: Favorite) {

        favorite.apply {
            val favFavorite = Favorite(
                author = author,
                bookshelves = bookshelves,
                copyright = copyright,
                download_count = download_count,
                formats = formats,
                id = id,
                languages = languages,
                media_type = media_type,
                subjects =  subjects,
                title = title,
                translator = translator,
                isRead = true
            )

            viewModelScope.launch {
                _favoriteBook.value = FavoriteBookState(
                    isLoading = true
                )
                favoriteBookRepository.saveBook(favFavorite)
                _favoriteBook.value = FavoriteBookState(
                    message = "Book added to favorites"
                )
            }
        }
    }

    fun getBookIsRead(favorite: Favorite) {
        viewModelScope.launch {
            favoriteBookRepository.getAllBooks().collectLatest { books ->
                for (i in books.indices) {
                    if (books[i].id == favorite.id) {
                        isRead.value  = true
                    }
                }
            }
        }
    }
    
    @SuppressLint("Range")
    fun downloadBook(favorite: Favorite, activity: MainActivity): String {

        if (activity.checkStoragePermission()) {
            val filename = favorite.title.split(" ").joinToString(separator = "+") + ".epub"
            val manager = activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val uri = Uri.parse(favorite.formats.applicationepubzip)
            val request = DownloadManager.Request(uri)

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverRoaming(true)
                .setAllowedOverMetered(true)
                .setTitle(favorite.title)
                .setDescription(favorite.title + favorite.bookshelves.joinToString(separator = ","))
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    DOWNLOAD_DIR + "/" + filename
                )

            // start downloading.
            val downloadId = manager.enqueue(request)

            viewModelScope.launch(Dispatchers.IO) {
                var isDownloadFinished = false

                while (!isDownloadFinished) {

                    val cursor = manager.query(DownloadManager.Query().setFilterById(downloadId))

                    if (cursor.moveToFirst()) {

                        when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                            DownloadManager.STATUS_SUCCESSFUL -> {
                                insertIntoDB(favorite)
                                isDownloadFinished = true
                            }
                            DownloadManager.STATUS_PAUSED, DownloadManager.STATUS_PENDING -> {}
                            DownloadManager.STATUS_FAILED -> {
                                isDownloadFinished = true
                            }
                        }
                    } else {
                        // Download cancelled by the user.
                        isDownloadFinished = true
                    }

                    cursor.close()
                }
            }

            return "Downloading"
        }
        else {
            return "You need to allow permissions"
        }
    }

    private fun insertIntoDB(favorite: Favorite) {
        saveAsFavorite(favorite = favorite)
    }
}