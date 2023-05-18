package com.loki.booko.data.local.mappers

import com.loki.booko.data.remote.response.BookDto
import com.loki.booko.domain.models.Favorite
import com.loki.booko.domain.models.BookEntity
import com.loki.booko.domain.models.BookItem

fun BookDto.toBookEntity():  BookEntity {

    return BookEntity(
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

fun BookEntity.toBookItem(): BookItem {

    return BookItem(
        author = author,
        bookshelves = bookshelves,
        copyright = copyright,
        download_count = download_count,
        formats = formats,
        id = id,
        languages = languages,
        media_type = media_type,
        subjects = subjects,
        title = title,
        translator = translator
    )
}

fun Favorite.toBookItem(): BookItem {
    return BookItem(
        author = author,
        bookshelves = bookshelves,
        copyright = copyright,
        download_count = download_count,
        formats = formats,
        id = id,
        languages = languages,
        media_type = media_type,
        subjects = subjects,
        title = title,
        translator = translator
    )
}

fun BookDto.toFavoriteBook(): Favorite {

    return Favorite(
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