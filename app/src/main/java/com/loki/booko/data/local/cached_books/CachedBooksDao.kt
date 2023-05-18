package com.loki.booko.data.local.cached_books

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.loki.booko.domain.models.BookEntity

@Dao
interface CachedBooksDao {

    @Upsert
    suspend fun upsertAll(books: List<BookEntity>)

    @Query("SELECT * FROM book_entity")
    fun pagingSource(): PagingSource<Int, BookEntity>

    @Query("DELETE FROM book_entity")
    suspend fun clearAll()
}