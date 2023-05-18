package com.loki.booko.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.loki.booko.data.local.BookDatabase
import com.loki.booko.data.remote.BookApi
import com.loki.booko.data.remote.BooksMediator
import com.loki.booko.data.repository.remote.BooksRepositoryImpl
import com.loki.booko.domain.models.BookEntity
import com.loki.booko.domain.repository.remote.BooksRepository
import com.loki.booko.domain.use_cases.books.BookDetailUseCase
import com.loki.booko.domain.use_cases.books.BookUseCase
import com.loki.booko.util.Constants.BOOK_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookModule {

    @Singleton
    @Provides
    fun provideBookApi(): BookApi {

        return Retrofit.Builder()
            .baseUrl(BOOK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApi::class.java)
    }

    @Singleton
    @Provides
    fun provideBookRepository(api: BookApi): BooksRepository {
        return BooksRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideBookUseCase(repository: BooksRepository): BookUseCase {
        return BookUseCase(
            getBookDetail = BookDetailUseCase(repository)
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Singleton
    @Provides
    fun provideBookMediator(api: BookApi, db: BookDatabase): Pager<Int, BookEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 32
            ),
            remoteMediator = BooksMediator(
                api = api,
                db = db
            ),
            pagingSourceFactory = {
                db.cachedBooksDao.pagingSource()
            }
        )
    }
}