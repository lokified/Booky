package com.loki.booko.domain.use_cases.books

import com.loki.booko.data.remote.response.toBookDto
import com.loki.booko.domain.models.BookDto
import com.loki.booko.domain.repository.remote.BooksRepository
import com.loki.booko.util.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class BookDetailUseCase(
    private val repository: BooksRepository
) {

    operator fun invoke(bookId: Int) = flow<Resource<BookDto>> {
        try {
            emit(Resource.Loading<BookDto>(data = null))
            emit(Resource.Success<BookDto>(data = repository.getBookDetail(bookId).toBookDto()))
        }
        catch (e: HttpException) {
            emit(Resource.Error<BookDto>(e.localizedMessage ?: "An unexpected error occurred", data = null))
        }
        catch (e: IOException) {
            emit(Resource.Error<BookDto>("check your internet connection", data = null))
        }
    }
}