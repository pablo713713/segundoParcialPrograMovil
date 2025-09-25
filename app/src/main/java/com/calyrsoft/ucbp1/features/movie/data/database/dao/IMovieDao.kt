package com.calyrsoft.ucbp1.features.movie.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(movies: List<MovieEntity>)

    // lectura en caliente para UI (orden por popularidad desc)
    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun observePopular(): Flow<List<MovieEntity>>

    // lectura puntual (para fallback si falla red)
    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    suspend fun getPopularOnce(): List<MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}
