package com.aplus.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aplus.domain.model.Genres
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresTable {
    @Query("SELECT * FROM genres")
    fun getList(): Flow<List<Genres>>

    @Query("SELECT * FROM genres WHERE id = :id")
    fun getSingle(id: Int): Genres

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(movies: List<Genres>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingle(movies: Genres): Long

    @Query("DELETE FROM genres")
    fun deleteAll(): Int

    @Query("DELETE FROM genres WHERE id = :id")
    fun deleteSingle(id: Int): Int
}