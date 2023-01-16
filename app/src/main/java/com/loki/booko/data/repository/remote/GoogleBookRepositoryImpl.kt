package com.loki.booko.data.repository.remote

import com.loki.booko.data.remote.GoogleBooksApi
import com.loki.booko.data.remote.googleBookResponse.Item
import com.loki.booko.data.remote.googleBookResponse.VolumeInfo
import com.loki.booko.data.remote.response.toBookDto
import com.loki.booko.domain.models.BookDto
import com.loki.booko.domain.repository.remote.GoogleBookRepository
import com.loki.booko.util.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GoogleBookRepositoryImpl @Inject constructor(
    private val api: GoogleBooksApi
): GoogleBookRepository {

    override suspend  fun getBookInfo(bookName: String) : List<Item> {

        val listItem = api.getBookInfo(bookName).items
        return if (listItem.isNotEmpty()) {
            listItem
        } else {
            emptyList()
        }
    }
}