package com.loki.booko.domain.models

import com.loki.booko.data.remote.response.Author
import com.loki.booko.data.remote.response.Formats
import com.loki.booko.data.remote.response.Translator

data class BookItem(
    val author: List<Author>?,
    val bookshelves: List<String>,
    val copyright: Boolean,
    val download_count: Int,
    val formats: Formats,
    val id: Int,
    val languages: List<String>,
    val media_type: String,
    val subjects: List<String>,
    val title: String,
    val translator: List<Translator>?,
    val isRead: Boolean = false
)
