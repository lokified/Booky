package com.loki.booko.data.remote.response

data class BookDto(
    val id: Int,
    val title: String,
    val authors: List<Author>,
    val translators: List<Translator>,
    val subjects: List<String>,
    val bookshelves: List<String>,
    val languages: List<String>,
    val copyright: Boolean,
    val media_type: String,
    val formats: Formats,
    val download_count: Int
)