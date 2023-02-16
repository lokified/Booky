package com.loki.booko.data.repository.remote

import com.loki.booko.data.remote.BookApi
import com.loki.booko.data.remote.response.Book
import com.loki.booko.data.remote.response.BookResponse
import com.loki.booko.data.remote.response.toBookDto
import com.loki.booko.domain.models.BookDto
import com.loki.booko.domain.repository.remote.BooksRepository
import com.loki.booko.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val bookApi: BookApi
): BooksRepository {

    override suspend fun getBooks() = flow<Resource<List<BookDto>>> {
        try {
            emit(Resource.Loading(data = null))
            emit(Resource.Success(data = bookApi.getBooks().results.map { it.toBookDto() }))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred", data = null))
        } catch (e: IOException) {
            emit(Resource.Error("check your internet connection", data = null))
        }
    }

    override suspend fun getBookDetail(bookId: Int): Book {
        return bookApi.getBookDetail(bookId)
    }

    override suspend fun searchBook(term: String) = flow<Resource<List<BookDto>>> {
        try {
            emit(Resource.Loading(data = null))
            emit(Resource.Success(data = bookApi.searchBook(term)?.results?.map { it.toBookDto() } ?: emptyList()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred", data = null))
        } catch (e: IOException) {
            emit(Resource.Error("check your internet connection", data = null))
        }
    }
}