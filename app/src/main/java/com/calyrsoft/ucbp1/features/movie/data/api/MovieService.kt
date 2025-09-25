package com.calyrsoft.ucbp1.features.movie.data.api

import com.calyrsoft.ucbp1.features.movie.data.api.dto.MoviePageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("3/discover/movie") // ← sin slash inicial
    suspend fun fetchPopularMovies(
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("api_key") apiKey: String
    ): Response<MoviePageDto>
}