package com.calyrsoft.ucbp1.features.book.domain.repository

import com.calyrsoft.ucbp1.features.book.domain.model.BookModel

interface IBookRepository {
    suspend fun search(query: String): Result<List<BookModel>>
}