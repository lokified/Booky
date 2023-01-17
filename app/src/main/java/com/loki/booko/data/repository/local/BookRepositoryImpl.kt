package com.loki.booko.data.repository.local

import com.loki.booko.data.local.book.BookDao
import com.loki.booko.data.remote.response.toBookDto
import com.loki.booko.domain.models.BookDto
import com.loki.booko.domain.repository.local.BookRepository
import com.loki.booko.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
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

    override suspend fun deleteAll() {
        bookDao.deleteAll()
    }
}