package com.loki.booko.domain.repository.remote

import com.loki.booko.data.remote.response.Book
import com.loki.booko.data.remote.response.BookResponse
import com.loki.booko.domain.models.BookDto
import com.loki.booko.util.Resource
import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    suspend fun getBooks(page: Int): Flow<Resource<List<BookDto>>>

    suspend fun getBookDetail(bookId: Int): Book

    suspend fun searchBook(term: String): Flow<Resource<List<BookDto>>>
}