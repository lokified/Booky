package com.loki.booko.data.repository.local

import com.loki.booko.data.local.book.BookDao
import com.loki.booko.domain.models.BookDto
import com.loki.booko.domain.repository.local.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepositoryImpl (
    private val bookDao: BookDao
): BookRepository {

    override fun getAllBooks(): Flow<List<BookDto>> {
        return bookDao.getAllBooks()
    }

    override suspend fun saveBook(book: BookDto) {
        bookDao.saveBook(book)
    }

    override suspend fun deleteBook(book: BookDto) {
        bookDao.deleteBook(book)
    }
}