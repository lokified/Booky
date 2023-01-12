package com.loki.booko.data.local.searchTerm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loki.booko.domain.models.Term
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchTermDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSearchTerm(term: Term)

    @Query("SELECT * FROM search_term")
    fun getAllSearchTerms(): Flow<List<Term>>

    @Delete
    suspend fun deleteSearchTerm(term: Term)
}