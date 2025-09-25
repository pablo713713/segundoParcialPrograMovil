package com.calyrsoft.ucbp1.features.dollar.domain.model

data class DollarModel(
    val id: Long = 0L,
    val dollarOfficial: String? = null,
    val dollarParallel: String? = null,
    val timestamp: Long = 0L
)