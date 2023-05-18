package com.loki.booko.data.repository.local

import com.loki.booko.data.local.favoriites.FavoriteDao
import com.loki.booko.domain.models.Favorite
import com.loki.booko.domain.repository.local.FavoriteBookRepository
import kotlinx.coroutines.flow.Flow

class FavoriteBookRepositoryImpl (
    private val favoriteDao: FavoriteDao
): FavoriteBookRepository {

    override fun getAllBooks(): Flow<List<Favorite>> {
        return favoriteDao.getAllBooks()
    }

    override suspend fun saveBook(favorite: Favorite) {
        favoriteDao.saveBook(favorite)
    }

    override suspend fun deleteBook(favorite: Favorite) {
        favoriteDao.deleteBook(favorite)
    }

    override suspend fun deleteAll() {
        favoriteDao.deleteAll()
    }
}