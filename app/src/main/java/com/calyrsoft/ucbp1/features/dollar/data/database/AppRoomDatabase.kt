package com.calyrsoft.ucbp1.features.dollar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.calyrsoft.ucbp1.features.dollar.data.database.dao.IDollarDao
import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
// 👉 imports de Book
import com.calyrsoft.ucbp1.features.book.data.database.dao.IBookDao
import com.calyrsoft.ucbp1.features.book.data.database.entity.BookEntity

@Database(
    entities = [
        DollarEntity::class,
        BookEntity::class
    ],
    version = 2,
    exportSchema = true
)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun dollarDao(): IDollarDao
    abstract fun bookDao(): IBookDao // ← nuevo DAO

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    "dollar_db" // mantenemos el mismo nombre de BD
                )
                    .fallbackToDestructiveMigration() // ← evita crashes por cambios de esquema en dev
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
