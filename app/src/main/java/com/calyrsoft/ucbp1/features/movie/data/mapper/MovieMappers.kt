package com.calyrsoft.ucbp1.features.movie.data.mapper

import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel

fun MovieEntity.toModel(): MovieModel =
    MovieModel(
        id = id,
        title = title,
        pathUrl = posterUrl,
        popularity = popularity
    )

fun MovieModel.toEntity(): MovieEntity =
    MovieEntity(
        id = id,
        title = title,
        posterUrl = pathUrl,
        popularity = popularity
    )
