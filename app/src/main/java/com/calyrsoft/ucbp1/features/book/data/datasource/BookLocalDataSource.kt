package com.calyrsoft.ucbp1.features.book.data.datasource

import com.calyrsoft.ucbp1.features.book.data.database.dao.IBookDao
import com.calyrsoft.ucbp1.features.book.data.mapper.toDomain
import com.calyrsoft.ucbp1.features.book.data.mapper.toEntityForLike
import com.calyrsoft.ucbp1.features.book.domain.model.BookModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookLocalDataSource(
    private val dao: IBookDao
) {
    // Observa los libros guardados (más nuevos primero)
    fun observeLiked(): Flow<List<BookModel>> =
        dao.observeLiked().map { list -> list.map { it.toDomain() } }

    // Guarda un libro como "me gusta". Devuelve true si lo insertó, false si ya existía (IGNORE)
    suspend fun like(book: BookModel): Boolean {
        val id = dao.insert(book.toEntityForLike())
        return id != -1L
    }

    // (Opcional) Des-like si después lo necesitas
    suspend fun deleteById(id: Long) = dao.deleteById(id)
}
