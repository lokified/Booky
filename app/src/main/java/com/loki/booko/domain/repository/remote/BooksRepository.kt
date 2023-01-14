package com.loki.booko.domain.repository.remote

import com.loki.booko.data.remote.response.Book
import com.loki.booko.data.remote.response.BookResponse

interface BooksRepository {

    suspend fun getBooks(): BookResponse

    suspend fun getBookDetail(bookId: Int): Book

    suspend fun searchBook(term: String): BookResponse?
}