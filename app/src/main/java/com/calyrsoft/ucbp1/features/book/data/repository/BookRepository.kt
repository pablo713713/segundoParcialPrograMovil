package com.calyrsoft.ucbp1.features.book.data.repository

import com.calyrsoft.ucbp1.features.book.data.datasource.BookRemoteDataSource
import com.calyrsoft.ucbp1.features.book.data.error.DataException
import com.calyrsoft.ucbp1.features.book.domain.error.Failure
import com.calyrsoft.ucbp1.features.book.domain.model.BookModel
import com.calyrsoft.ucbp1.features.book.domain.repository.IBookRepository

class BookRepository(
    val remoteDataSource: BookRemoteDataSource
) : IBookRepository {

    // antes: findByNick(value: String): Result<BookModel>
    // ahora: search(value: String): Result<List<BookModel>>
    override suspend fun search(value: String): Result<List<BookModel>> {
        if (value.isBlank()) {
            return Result.failure(Exception("El campo no puede estar vacio"))
        }
        val response = remoteDataSource.search(value)

        response.fold(
            onSuccess = { return Result.success(it) },
            onFailure = { exception ->
                val failure = when (exception) {
                    is DataException.Network -> Failure.NetworkConnection
                    is DataException.HttpNotFound -> Failure.NotFound
                    is DataException.NoContent -> Failure.EmptyBody
                    is DataException.Unknown -> Failure.Unknown(exception)
                    else -> Failure.Unknown(exception)
                }
                return Result.failure(failure)
            }
        )
    }
}
