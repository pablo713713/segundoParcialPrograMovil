package com.calyrsoft.ucbp1.features.book.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calyrsoft.ucbp1.features.book.data.database.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IBookDao {

    // Insert IGNORE para que el índice único evite duplicados
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: BookEntity): Long

    // Observa los “me gusta” (más nuevos primero)
    @Query("SELECT * FROM liked_books ORDER BY saved_at DESC")
    fun observeLiked(): Flow<List<BookEntity>>

    @Query("DELETE FROM liked_books WHERE id = :id")
    suspend fun deleteById(id: Long)
}
