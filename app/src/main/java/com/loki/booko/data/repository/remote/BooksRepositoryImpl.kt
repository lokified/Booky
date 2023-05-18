package com.loki.booko.data.repository.remote

import com.loki.booko.data.remote.BookApi
import com.loki.booko.data.remote.response.BookDto
import com.loki.booko.domain.repository.remote.BooksRepository
import com.loki.booko.util.bookListResponse
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val bookApi: BookApi
): BooksRepository {

    override suspend fun getBookDetail(bookId: Int): BookDto {
        return bookApi.getBookDetail(bookId)
    }

    override suspend fun searchBook(term: String) = bookListResponse(
        bookApi
            .searchBook(term)
            ?.results ?: emptyList()
    )
}