package com.calyrsoft.ucbp1.features.book.data.api.dto

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(
    @SerializedName("docs") val docs: List<BookDto>?
)