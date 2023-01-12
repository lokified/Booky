package com.loki.booko.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.loki.booko.data.local.book.BookDao
import com.loki.booko.data.local.searchTerm.SearchTermDao
import com.loki.booko.domain.BookTypeConverter
import com.loki.booko.domain.models.BookDto
import com.loki.booko.domain.models.Term

@Database(
    version = 1,
    exportSchema = false,
    entities = [BookDto::class, Term::class]
)

abstract class BookDatabase : RoomDatabase() {

    abstract val bookDao: BookDao

    abstract val searchTermDao: SearchTermDao

    companion object {
        const val DATABASE_NAME = "book_db"
    }
}