package com.loki.booko.presentation.book_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.loki.booko.data.remote.response.Formats
import com.loki.booko.data.remote.response.Translator
import com.loki.booko.domain.models.BookDto
import com.loki.booko.domain.repository.local.BookRepository
import com.loki.booko.domain.repository.remote.GoogleBookRepository
import com.loki.booko.domain.use_cases.books.BookUseCase
import com.loki.booko.util.Constants.BOOK_ID
import com.loki.booko.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookUseCase: BookUseCase,
    savedStateHandle: SavedStateHandle,
    private val googleBookRepository: GoogleBookRepository,
    private val bookRepository: BookRepository
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
                    val bookInfoList = googleBookRepository.getBookInfo(result.data?.title!!)

                    if(bookInfoList?.isEmpty() == true) {
                        _bookDetailState.value = BookDetailState(
                            errorMessage = "Could not find the book detail"
                        )
                    }
                    _bookDetailState.value = BookDetailState(
                        book = result.data,
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

    fun saveAsFavorite(bookDto: BookDto) {

        bookDto.apply {
            val book = BookDto(
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
                delay(1000L)
                bookRepository.saveBook(book)
                _favoriteBook.value = FavoriteBookState(
                    message = "Book added to favorites"
                )
            }
        }
    }

    fun getBookIsRead(bookDto: BookDto) {
        viewModelScope.launch {
            bookRepository.getAllBooks().collectLatest { books ->
                for (i in books.indices) {
                    if (books[i].id == bookDto.id) {
                        isRead.value  = true
                    }
                }
            }
        }
    }
}