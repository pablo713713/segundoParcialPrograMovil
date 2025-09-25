package com.calyrsoft.ucbp1.features.book.data.api

import com.calyrsoft.ucbp1.features.book.data.api.dto.SearchResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("search.json")
    suspend fun searchBooks(@Query("q") query: String): Response<SearchResponseDto>
}