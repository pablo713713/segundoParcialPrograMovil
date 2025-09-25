package com.calyrsoft.ucbp1.features.dollar.data.mapper

import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel

fun DollarEntity.toModel(): DollarModel {
    return DollarModel(
        id = id,
        dollarOfficial = dollarOfficial,
        dollarParallel = dollarParallel,
        dollarUsdt = dollarUsdt,   // 👈 nuevo
        dollarUsdc = dollarUsdc,   // 👈 nuevo
        timestamp = timestamp
    )
}

fun DollarModel.toEntity(): DollarEntity {
    return DollarEntity(
        dollarOfficial = dollarOfficial,
        dollarParallel = dollarParallel,
        dollarUsdt = dollarUsdt,   // 👈 nuevo
        dollarUsdc = dollarUsdc    // 👈 nuevo
        // timestamp se autogenera en la Entity
    )
}
