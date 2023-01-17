package com.loki.booko.data.local.book

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loki.booko.domain.models.BookDto
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(book: BookDto)

    @Query("SELECT * FROM book")
    fun getAllBooks(): Flow<List<BookDto>>

    @Delete
    suspend fun deleteBook(book: BookDto)

    @Query("DELETE FROM book")
    suspend fun deleteAll()
}