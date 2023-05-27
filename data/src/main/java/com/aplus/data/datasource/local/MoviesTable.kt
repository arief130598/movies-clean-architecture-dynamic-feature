package com.aplus.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aplus.domain.model.Movies
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesTable {
    @Query("SELECT * FROM movies")
    fun getList(): Flow<List<Movies>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getSingle(id: Int): Movies

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(movies: List<Movies>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingle(movies: Movies): Long

    @Query("DELETE FROM movies")
    fun deleteAll(): Int

    @Query("DELETE FROM movies WHERE id = :id")
    fun deleteSingle(id: Int): Int
}