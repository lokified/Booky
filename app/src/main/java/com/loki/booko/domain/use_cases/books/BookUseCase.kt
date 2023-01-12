package com.loki.booko.domain.use_cases.books

data class BookUseCase(
    val getBookList: BookListUseCase,
    val getBookDetail: BookDetailUseCase
)
