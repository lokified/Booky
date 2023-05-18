package com.loki.booko.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.loki.booko.data.local.cached_books.CachedBooksDao
import com.loki.booko.data.local.favoriites.FavoriteDao
import com.loki.booko.data.local.searchTerm.SearchTermDao
import com.loki.booko.domain.models.Favorite
import com.loki.booko.domain.models.BookEntity
import com.loki.booko.domain.models.Term

@Database(
    version = 3,
    exportSchema = false,
    entities = [Favorite::class, Term::class, BookEntity::class]
)

abstract class BookDatabase : RoomDatabase() {

    abstract val favoriteDao: FavoriteDao

    abstract val searchTermDao: SearchTermDao

    abstract val cachedBooksDao: CachedBooksDao

    companion object {
        const val DATABASE_NAME = "book_db"

    }
}