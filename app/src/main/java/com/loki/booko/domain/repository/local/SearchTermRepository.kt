package com.loki.booko.domain.repository.local

import com.loki.booko.domain.models.Term
import kotlinx.coroutines.flow.Flow

interface SearchTermRepository {

    fun getAllTerms(): Flow<List<Term>>

    suspend fun saveSearchTerm(term: Term)

    suspend fun deleteSearchTerm(term: Term)
}