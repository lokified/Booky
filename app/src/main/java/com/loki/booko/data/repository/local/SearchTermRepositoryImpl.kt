package com.loki.booko.data.repository.local

import com.loki.booko.data.local.searchTerm.SearchTermDao
import com.loki.booko.domain.models.Term
import com.loki.booko.domain.repository.local.SearchTermRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTermRepositoryImpl (
    private val searchTermDao: SearchTermDao
): SearchTermRepository {

    override fun getAllTerms(): Flow<List<Term>> {
        return searchTermDao.getAllSearchTerms()
    }

    override suspend fun saveSearchTerm(term: Term) {
        searchTermDao.saveSearchTerm(term)
    }

    override suspend fun deleteSearchTerm(term: Term) {
        searchTermDao.deleteSearchTerm(term)
    }
}