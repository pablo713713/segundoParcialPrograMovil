package com.calyrsoft.ucbp1.features.book.domain.model

data class BookModel(
    val title: String,
    val authors: List<String>,
    val year: Int?
)