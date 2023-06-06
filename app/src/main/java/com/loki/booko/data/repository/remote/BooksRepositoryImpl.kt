package com.loki.booko.data.repository.remote

import com.loki.booko.data.remote.BookApi
import com.loki.booko.data.remote.response.BookDto
import com.loki.booko.domain.repository.remote.BooksRepository
import com.loki.booko.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val bookApi: BookApi
): BooksRepository {

    override suspend fun getBookDetail(bookId: Int): BookDto {
        return bookApi.getBookDetail(bookId)
    }

    override suspend fun searchBook(term: String): Flow<Resource<List<BookDto>>> = flow {

        try {
            emit(Resource.Loading(data = null))
            emit(
                Resource.Success(
                    data = bookApi
                        .searchBook(term)
                        ?.results ?: emptyList()
                )
            )
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred", data = null))
        } catch (e: IOException) {
            emit(Resource.Error("check your internet connection", data = null))
        }
    }
}