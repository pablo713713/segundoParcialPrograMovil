package com.calyrsoft.ucbp1.features.dollar.domain.model

data class DollarModel(
    val id: Long = 0L,
    val dollarOfficial: String? = null,
    val dollarParallel: String? = null,
    val dollarUsdt: String? = null,   // ← nuevo
    val dollarUsdc: String? = null,   // ← nuevo
    val timestamp: Long = 0L
)
