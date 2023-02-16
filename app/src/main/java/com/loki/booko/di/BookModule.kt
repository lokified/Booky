package com.loki.booko.di

import com.loki.booko.data.remote.BookApi
import com.loki.booko.data.remote.GoogleBooksApi
import com.loki.booko.data.repository.remote.BooksRepositoryImpl
import com.loki.booko.data.repository.remote.GoogleBookRepositoryImpl
import com.loki.booko.domain.repository.remote.BooksRepository
import com.loki.booko.domain.repository.remote.GoogleBookRepository
import com.loki.booko.domain.use_cases.books.BookDetailUseCase
import com.loki.booko.domain.use_cases.books.BookUseCase
import com.loki.booko.util.Constants.BOOK_BASE_URL
import com.loki.booko.util.Constants.GOOGLE_BASE_URL
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
    fun provideGoogleBooksApi(): GoogleBooksApi {

        return Retrofit.Builder()
            .baseUrl(GOOGLE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleBooksApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGoogleBookRepository(api: GoogleBooksApi): GoogleBookRepository {
        return GoogleBookRepositoryImpl(api)
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
}