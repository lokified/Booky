package com.loki.booko.presentation.book_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.booko.domain.repository.remote.GoogleBookRepository
import com.loki.booko.domain.use_cases.books.BookUseCase
import com.loki.booko.util.Constants.BOOK_ID
import com.loki.booko.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookUseCase: BookUseCase,
    savedStateHandle: SavedStateHandle,
    private val googleBookRepository: GoogleBookRepository,
): ViewModel() {

    private val _bookDetailState = mutableStateOf(BookDetailState())
    val bookDetailState: State<BookDetailState> = _bookDetailState

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
}