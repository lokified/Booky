package com.loki.booko.di

import com.loki.booko.data.remote.GoogleBooksApi
import com.loki.booko.data.repository.remote.GoogleBookRepositoryImpl
import com.loki.booko.domain.repository.remote.GoogleBookRepository
import com.loki.booko.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoogleBookModule {

    @Singleton
    @Provides
    fun provideGoogleBooksApi(): GoogleBooksApi {

        return Retrofit.Builder()
            .baseUrl(Constants.GOOGLE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleBooksApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGoogleBookRepository(api: GoogleBooksApi): GoogleBookRepository {
        return GoogleBookRepositoryImpl(api)
    }
}