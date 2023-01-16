package com.loki.booko.data.remote

import com.loki.booko.BuildConfig
import com.loki.booko.data.remote.googleBookResponse.GoogleBookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {

    @GET("volumes?&startIndex=0&maxResults=1&key=${BuildConfig.API_KEY}")
    suspend fun getBookInfo(
       @Query("q") bookName: String
    ): GoogleBookResponse
}