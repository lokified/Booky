package com.loki.booko.data.repository.remote

import com.loki.booko.data.remote.BookApi
import com.loki.booko.data.remote.response.Book
import com.loki.booko.data.remote.response.toBookDto
import com.loki.booko.domain.repository.remote.BooksRepository
import com.loki.booko.util.bookListResponse
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val bookApi: BookApi
): BooksRepository {

    override suspend fun getBooks(page: Int) = bookListResponse(
        bookApi
            .getBooks(page)
            .results
            .map { it.toBookDto() }
    )

    override suspend fun getBookDetail(bookId: Int): Book {
        return bookApi.getBookDetail(bookId)
    }

    override suspend fun searchBook(term: String) = bookListResponse(
        bookApi
            .searchBook(term)
            ?.results
            ?.map { it.toBookDto() } ?: emptyList()
    )
}