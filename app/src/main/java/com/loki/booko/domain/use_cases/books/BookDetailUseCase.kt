package com.loki.booko.domain.use_cases.books

import com.loki.booko.data.local.mappers.toFavoriteBook
import com.loki.booko.domain.models.Favorite
import com.loki.booko.domain.repository.remote.BooksRepository
import com.loki.booko.util.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class BookDetailUseCase(
    private val repository: BooksRepository
) {

    operator fun invoke(bookId: Int) = flow<Resource<Favorite>> {
        try {
            emit(Resource.Loading<Favorite>(data = null))
            emit(Resource.Success<Favorite>(data = repository.getBookDetail(bookId).toFavoriteBook()))
        }
        catch (e: HttpException) {
            emit(Resource.Error<Favorite>(e.localizedMessage ?: "An unexpected error occurred", data = null))
        }
        catch (e: IOException) {
            emit(Resource.Error<Favorite>("check your internet connection", data = null))
        }
    }
}