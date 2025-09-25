package com.calyrsoft.ucbp1.features.book.data.mapper

import com.calyrsoft.ucbp1.features.book.data.database.entity.BookEntity
import com.calyrsoft.ucbp1.features.book.domain.model.BookModel

// Entity -> Domain
fun BookEntity.toDomain(): BookModel =
    BookModel(
        title = title,
        authors = if (authorsText.isBlank()) emptyList()
        else authorsText.split(",").map { it.trim() },
        year = year
    )

// Domain -> Entity (para guardar "me gusta")
fun BookModel.toEntityForLike(): BookEntity =
    BookEntity(
        title = title,
        authorsText = authors.joinToString(", "),
        year = year
    )
