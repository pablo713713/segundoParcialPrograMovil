package com.calyrsoft.ucbp1.features.book.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// Índice único para evitar duplicados del mismo libro (título+autores+año).
@Entity(
    tableName = "liked_books",
    indices = [Index(value = ["title", "authors_text", "year"], unique = true)]
)
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "title") val title: String,
    // Guardamos autores como texto "autor1, autor2, autor3"
    @ColumnInfo(name = "authors_text") val authorsText: String,
    @ColumnInfo(name = "year") val year: Int?,
    // Para listar los guardados del más nuevo al más antiguo
    @ColumnInfo(name = "saved_at") val savedAt: Long = System.currentTimeMillis()
)
