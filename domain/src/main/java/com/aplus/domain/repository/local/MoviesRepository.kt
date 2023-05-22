package com.aplus.domain.repository.local

import com.aplus.domain.model.Movies

interface MoviesRepository {
    suspend fun getSingle(id: Int): Movies

    suspend fun getList(): List<Movies>

    suspend fun insertList(movies: List<Movies>): List<Long>

    suspend fun insertSingle(movies: Movies): Long

    suspend fun deleteAll(): Int

    suspend fun deleteSingle(id: Int): Int
}