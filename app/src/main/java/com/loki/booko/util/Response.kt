package com.loki.booko.util

import com.loki.booko.domain.models.BookDto
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

fun bookListResponse(data: List<BookDto>) = flow<Resource<List<BookDto>>> {

    try {
        emit(Resource.Loading(data = null))
        emit(Resource.Success(data = data))
    } catch (e: HttpException) {
        emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred", data = null))
    } catch (e: IOException) {
        emit(Resource.Error("check your internet connection", data = null))
    }
}