package com.calyrsoft.ucbp1.features.dollar.data.datasource

import com.calyrsoft.ucbp1.features.dollar.data.database.dao.IDollarDao
import com.calyrsoft.ucbp1.features.dollar.data.mapper.toEntity
import com.calyrsoft.ucbp1.features.dollar.data.mapper.toModel
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DollarLocalDataSource(
    val dao: IDollarDao
) {

    suspend fun getList(): List<DollarModel> {
        return dao.getList().map { it.toModel() }
    }

    fun observeHistory(): Flow<List<DollarModel>> =
        dao.observeHistory().map { list -> list.map { it.toModel() } }

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    suspend fun inserTDollars(list: List<DollarModel>) {
        val dollarEntity = list.map { it.toEntity() }
        dao.insertDollars(dollarEntity)
    }

    suspend fun insert(dollar: DollarModel) {
        dao.insert(dollar.toEntity())
    }

    suspend fun deleteById(id: Long) = dao.deleteById(id)
}
