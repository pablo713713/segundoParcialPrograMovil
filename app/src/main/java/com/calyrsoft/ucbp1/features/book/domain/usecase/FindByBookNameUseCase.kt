package com.calyrsoft.ucbp1.features.book.domain.usecase

import com.calyrsoft.ucbp1.features.book.domain.model.BookModel
import com.calyrsoft.ucbp1.features.book.domain.repository.IBookRepository
import kotlinx.coroutines.delay

class FindByBookNameUseCase(
    private val repository: IBookRepository
) {
    suspend operator fun invoke(query: String): Result<List<BookModel>> {
        // opcional: pequeña demora para mostrar Loading (si quieres la mantienes)
        delay(500)
        return repository.search(query)
    }
}
