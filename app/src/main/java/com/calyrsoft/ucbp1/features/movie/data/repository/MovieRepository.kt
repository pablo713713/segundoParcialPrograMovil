package com.calyrsoft.ucbp1.features.movie.data.repository

import com.calyrsoft.ucbp1.features.movie.data.datasource.MovieLocalDataSource
import com.calyrsoft.ucbp1.features.movie.data.datasource.MovieRemoteDataSource
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMoviesRepository

class MovieRepository(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) : IMoviesRepository {

    override suspend fun fetchPopularMovies(): Result<List<MovieModel>> {
        val remote = movieRemoteDataSource.fetchPopularMovies()
        return remote.fold(
            onSuccess = { list ->
                // guarda/actualiza cache
                movieLocalDataSource.upsertAll(list)
                Result.success(list)
            },
            onFailure = { err ->
                // fallback a cache si existe
                val cached = movieLocalDataSource.getPopularOnce()
                if (cached.isNotEmpty()) Result.success(cached)
                else Result.failure(err)
            }
        )
    }
}
