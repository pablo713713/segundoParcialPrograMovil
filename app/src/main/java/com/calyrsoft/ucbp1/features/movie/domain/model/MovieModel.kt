package com.calyrsoft.ucbp1.features.movie.domain.model

data class MovieModel(
    val id: Long,            // necesario para upsert en Room
    val title: String,
    val pathUrl: String?,    // puede venir null si no hay poster
    val popularity: Double   // para ordenar y actualizar
)
