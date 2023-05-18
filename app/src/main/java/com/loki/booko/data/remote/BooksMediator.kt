package com.loki.booko.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.loki.booko.data.local.BookDatabase
import com.loki.booko.data.local.mappers.toBookEntity
import com.loki.booko.data.remote.response.BookDto
import com.loki.booko.domain.models.BookEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BooksMediator(
    private val api: BookApi,
    private val db: BookDatabase
): RemoteMediator<Int, BookEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookEntity>
    ): MediatorResult {

        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    if (lastItem == null) {
                        1
                    } else {
                        state.config.pageSize + 1
                    }
                }
            }

            delay(3000L)

            val books = api.getBooks(page = loadKey).results

            db.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    db.cachedBooksDao.clearAll()
                }

                val bookEntities = books.map { it.toBookEntity() }
                db.cachedBooksDao.upsertAll(bookEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = books.isEmpty()
            )
        }
        catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}