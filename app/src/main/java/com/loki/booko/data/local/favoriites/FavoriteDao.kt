package com.loki.booko.data.local.favoriites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loki.booko.domain.models.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(favorite: Favorite)

    @Query("SELECT * FROM favorite")
    fun getAllBooks(): Flow<List<Favorite>>

    @Delete
    suspend fun deleteBook(favorite: Favorite)

    @Query("DELETE FROM favorite")
    suspend fun deleteAll()
}