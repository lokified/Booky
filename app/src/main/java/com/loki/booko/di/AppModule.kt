package com.loki.booko.di

import android.app.Application
import androidx.room.Room
import com.loki.booko.data.local.BookDatabase
import com.loki.booko.data.repository.local.BookRepositoryImpl
import com.loki.booko.data.repository.local.SearchTermRepositoryImpl
import com.loki.booko.domain.repository.local.BookRepository
import com.loki.booko.domain.repository.local.SearchTermRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBookDatabase(app: Application): BookDatabase {

        return Room.databaseBuilder(
            app,
            BookDatabase::class.java,
            BookDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookRepository(bookDatabase: BookDatabase): BookRepository {
        return BookRepositoryImpl(bookDatabase.bookDao)
    }

    @Provides
    @Singleton
    fun provideSearchTermRepository(bookDatabase: BookDatabase): SearchTermRepository {
        return SearchTermRepositoryImpl(bookDatabase.searchTermDao)
    }
}