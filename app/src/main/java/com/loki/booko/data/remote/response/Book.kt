package com.loki.booko.data.remote.response

import com.loki.booko.domain.models.BookDto

data class Book(
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

fun Book.toBookDto(): BookDto {

    return BookDto(
        author = authors,
        bookshelves = bookshelves,
        copyright = copyright,
        download_count = download_count,
        formats = formats,
        id = id,
        languages = languages,
        media_type = media_type,
        subjects = subjects,
        title = title,
        translator = translators
    )
}