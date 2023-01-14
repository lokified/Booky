package com.loki.booko.data.repository.remote

import com.loki.booko.data.remote.BookApi
import com.loki.booko.data.remote.response.Book
import com.loki.booko.data.remote.response.BookResponse
import com.loki.booko.domain.repository.remote.BooksRepository
import com.loki.booko.util.Resource
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val bookApi: BookApi
): BooksRepository {

    override suspend fun getBooks(): BookResponse {
        return bookApi.getBooks()
    }

    override suspend fun getBookDetail(bookId: Int): Book {
        return bookApi.getBookDetail(bookId)
    }

    override suspend fun searchBook(term: String): BookResponse? {
        return bookApi.searchBook(term)
    }
}