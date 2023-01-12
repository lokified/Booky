package com.loki.booko.data.remote.response

import com.loki.booko.domain.models.BookDto

data class Book(
    val authors: List<Author>,
    val bookshelves: List<String>,
    val copyright: Boolean,
    val download_count: Int,
    val formats: Formats,
    val id: Int,
    val languages: List<String>,
    val media_type: String,
    val subjects: List<String>,
    val title: String,
    val translators: List<Translator>
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