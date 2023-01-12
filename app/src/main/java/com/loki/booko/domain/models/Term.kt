package com.loki.booko.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_term")
data class Term(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val searchTerm: String
)
