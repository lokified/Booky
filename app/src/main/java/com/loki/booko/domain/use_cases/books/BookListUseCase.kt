package com.loki.booko.domain.use_cases.books

import com.loki.booko.data.remote.response.toBookDto
import com.loki.booko.domain.models.BookDto
import com.loki.booko.domain.repository.remote.BooksRepository
import com.loki.booko.util.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class BookListUseCase (
    private val repository: BooksRepository
        ) {

    operator fun invoke() = flow<Resource<List<BookDto>>> {
        try {
            emit(Resource.Loading<List<BookDto>>(data = null))
            emit(Resource.Success<List<BookDto>>(data = repository.getBooks().results.map { it.toBookDto() }))
        }
        catch (e: HttpException) {
            emit(Resource.Error<List<BookDto>>(e.localizedMessage ?: "An unexpected error occurred", data = null))
        }
        catch (e: IOException) {
            emit(Resource.Error<List<BookDto>>("check your internet connection", data = null))
        }
    }
}