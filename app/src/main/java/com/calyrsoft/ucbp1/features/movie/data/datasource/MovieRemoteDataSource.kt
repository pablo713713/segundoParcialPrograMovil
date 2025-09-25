package com.calyrsoft.ucbp1.features.movie.data.datasource

import com.calyrsoft.ucbp1.features.movie.data.api.MovieService
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel

private const val TMDB_IMG_BASE = "https://image.tmdb.org/t/p/w185"

class MovieRemoteDataSource(
    private val movieServie: MovieService,
    private val apiKey: String
) {
    suspend fun fetchPopularMovies(): Result<List<MovieModel>> {
        val response = movieServie.fetchPopularMovies(apiKey = apiKey)
        return if (response.isSuccessful) {
            val moviePage = response.body()
            val list = moviePage?.results?.map { dto ->
                MovieModel(
                    id = dto.id,
                    title = dto.title,
                    pathUrl = dto.posterPath?.let { "https://image.tmdb.org/t/p/w185$it" },
                    popularity = dto.popularity
                )
            }.orEmpty()
            Result.success(list)
        } else {
            val code = response.code()
            val body = try { response.errorBody()?.string() } catch (_: Exception) { null }
            Result.failure(Exception("HTTP $code ${body ?: ""}".trim()))
        }
    }
}
