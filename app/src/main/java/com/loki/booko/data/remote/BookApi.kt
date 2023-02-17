package com.loki.booko.data.remote

import com.loki.booko.data.remote.response.Book
import com.loki.booko.data.remote.response.BookResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {

    @GET("books")
    suspend fun getBooks(
        @Query("page") page: Int
    ): BookResponse

    @GET("books/{id}")
    suspend fun getBookDetail(
        @Path("id") bookId: Int
    ): Book

    @GET("books")
    suspend fun searchBook(
        @Query("search") term: String
    ): BookResponse?
}