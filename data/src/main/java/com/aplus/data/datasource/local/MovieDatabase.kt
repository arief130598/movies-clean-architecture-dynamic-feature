package com.aplus.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aplus.data.utils.MovieDBConverters
import com.aplus.domain.model.Genres
import com.aplus.domain.model.Movies

/**
 *
 * Database Class for configuration Room Database</br>
 * Add inside entities for each class to add New Table</br>
 * Create abstract class for each DAO interface</br>
 * Each releasing the apps dont forgot to makesure DB Version is same with Apps Version
 *
 */
@Database(
    entities = [ // Add class to add new Table,
        Movies::class,
        Genres::class
    ],
    version = 10000, // Change db version with apps version
    exportSchema = false)

@TypeConverters(MovieDBConverters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract val moviesTable: MoviesTable
    abstract val genresTable: GenresTable

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "moviedb.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}