package com.calyrsoft.ucbp1.features.dollar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.calyrsoft.ucbp1.features.dollar.data.database.dao.IDollarDao
import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity

// Book
import com.calyrsoft.ucbp1.features.book.data.database.dao.IBookDao
import com.calyrsoft.ucbp1.features.book.data.database.entity.BookEntity

// Movie 👇
import com.calyrsoft.ucbp1.features.movie.data.database.dao.IMovieDao
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity

@Database(
    entities = [
        DollarEntity::class,
        BookEntity::class,
        MovieEntity::class,
    ],
    version = 4,
    exportSchema = true
)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun dollarDao(): IDollarDao
    abstract fun bookDao(): IBookDao
    abstract fun movieDao(): IMovieDao

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    "dollar_db"
                )
                    .fallbackToDestructiveMigration() // en dev evita crashes por cambios de esquema
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
