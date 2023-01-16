package com.loki.booko.domain.repository.remote

import com.loki.booko.data.remote.googleBookResponse.Item
import com.loki.booko.data.remote.googleBookResponse.VolumeInfo
import com.loki.booko.util.Resource
import kotlinx.coroutines.flow.Flow

interface GoogleBookRepository {

    suspend fun getBookInfo(bookName: String): List<Item>?
}