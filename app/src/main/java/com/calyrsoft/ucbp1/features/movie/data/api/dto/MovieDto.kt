package com.calyrsoft.ucbp1.features.movie.data.api.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: Long,
    @SerializedName("poster_path") val posterPath: String?, // puede venir null
    @SerializedName("title") val title: String,
    @SerializedName("popularity") val popularity: Double
)
