package com.loki.booko.data.remote.response

import java.io.Serializable

data class Author(
    val birth_year: Int,
    val death_year: Int,
    val name: String
): Serializable