package com.loki.booko.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.loki.booko.data.local.BookDatabase
import com.loki.booko.data.repository.local.FavoriteBookRepositoryImpl
import com.loki.booko.data.repository.local.SearchTermRepositoryImpl
import com.loki.booko.domain.network_service.NetworkConnectivityService
import com.loki.booko.domain.network_service.NetworkConnectivityServiceImpl
import com.loki.booko.domain.repository.local.FavoriteBookRepository
import com.loki.booko.domain.repository.local.SearchTermRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideBookRepository(bookDatabase: BookDatabase): FavoriteBookRepository {
        return FavoriteBookRepositoryImpl(bookDatabase.favoriteDao)
    }

    @Provides
    @Singleton
    fun provideSearchTermRepository(bookDatabase: BookDatabase): SearchTermRepository {
        return SearchTermRepositoryImpl(bookDatabase.searchTermDao)
    }

    @Provides
    @Singleton
    fun provideNetworkServiceContext(@ApplicationContext context: Context): NetworkConnectivityService {
        return NetworkConnectivityServiceImpl(context)
    }
}