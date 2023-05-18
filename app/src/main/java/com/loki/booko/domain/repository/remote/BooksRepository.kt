package com.loki.booko.domain.repository.remote

import com.loki.booko.data.remote.response.BookDto
import com.loki.booko.util.Resource
import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    suspend fun getBookDetail(bookId: Int): BookDto

    suspend fun searchBook(term: String): Flow<Resource<List<BookDto>>>
}