package com.loki.booko.domain.repository.local

import com.loki.booko.domain.models.BookDto
import com.loki.booko.util.Resource
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getAllBooks(): Flow<List<BookDto>>

    suspend fun saveBook(book: BookDto)

    suspend fun deleteBook(book: BookDto)
}