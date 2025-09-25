package com.calyrsoft.ucbp1.features.book.data.datasource

import com.calyrsoft.ucbp1.features.book.data.api.BookService
import com.calyrsoft.ucbp1.features.book.data.error.DataException
import com.calyrsoft.ucbp1.features.book.domain.model.BookModel

class BookRemoteDataSource(
    val bookService: BookService // ← dejo el nombre para no romper inyección
) {
    // antes: getUser(nick) -> Result<BookModel>
    // ahora: search(query) -> Result<List<BookModel>>
    suspend fun search(query: String): Result<List<BookModel>> {
        val response = bookService.searchBooks(query)
        if (response.isSuccessful) {
            val body = response.body()
            val docs = body?.docs.orEmpty()

            // Devolvemos éxito con lista (puede ser vacía: 0 resultados)
            val mapped = docs.map { dto ->
                BookModel(
                    title = dto.title ?: "",
                    authors = dto.authors ?: emptyList(),
                    year = dto.firstPublishYear
                )
            }
            return Result.success(mapped)
        } else if (response.code() == 404) {
            return Result.failure(DataException.HttpNotFound)
        } else {
            return Result.failure(DataException.Unknown(response.message()))
        }
    }
}
