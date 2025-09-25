package com.calyrsoft.ucbp1.features.book.data.api.dto

import com.google.gson.annotations.SerializedName

data class BookDto(
    @SerializedName("title") val title: String?,
    @SerializedName("author_name") val authors: List<String>?,        // puede ser null o lista vacía
    @SerializedName("first_publish_year") val firstPublishYear: Int?   // año opcional
)