package com.aplus.domain.repository.local

import com.aplus.domain.model.Genres

interface GenresRepository {
    suspend fun getSingle(id: Int): Genres

    suspend fun getList(): List<Genres>

    suspend fun insertList(genres: List<Genres>): List<Long>

    suspend fun insertSingle(genres: Genres): Long

    suspend fun deleteAll(): Int

    suspend fun deleteSingle(id: Int): Int
}