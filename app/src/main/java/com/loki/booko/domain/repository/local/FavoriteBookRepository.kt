package com.loki.booko.domain.repository.local

import com.loki.booko.domain.models.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteBookRepository {

    fun getAllBooks(): Flow<List<Favorite>>

    suspend fun saveBook(favorite: Favorite)

    suspend fun deleteBook(favorite: Favorite)

    suspend fun deleteAll()
}