package com.calyrsoft.ucbp1.features.movie.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String,
    // guardamos la URL completa (o null si no hay poster)
    @ColumnInfo(name = "poster_url") val posterUrl: String?,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis()
)
