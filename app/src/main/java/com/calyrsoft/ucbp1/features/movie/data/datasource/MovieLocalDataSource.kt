package com.calyrsoft.ucbp1.features.movie.data.datasource

import com.calyrsoft.ucbp1.features.movie.data.database.dao.IMovieDao
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity
import com.calyrsoft.ucbp1.features.movie.data.mapper.toEntity
import com.calyrsoft.ucbp1.features.movie.data.mapper.toModel
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieLocalDataSource(
    private val dao: IMovieDao
) {
    suspend fun upsertAll(models: List<MovieModel>) {
        dao.upsertAll(models.map { it.toEntity() })
    }

    fun observePopular(): Flow<List<MovieModel>> =
        dao.observePopular().map { list -> list.map { it.toModel() } }

    suspend fun getPopularOnce(): List<MovieModel> =
        dao.getPopularOnce().map { it.toModel() }

    suspend fun clearAll() = dao.clearAll()
}
