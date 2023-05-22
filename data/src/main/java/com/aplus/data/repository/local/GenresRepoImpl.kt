package com.aplus.data.repository.local

import com.aplus.data.di.MovieDatabase
import com.aplus.domain.model.Genres
import com.aplus.domain.repository.local.GenresRepository
import javax.inject.Inject

/**
 *
 * Repository to call from Database -> Genres Table <br/>
 * Repository name must be same from Genres DAO
 *
 */
class GenresRepoImpl @Inject constructor(private val db: MovieDatabase) : GenresRepository {

    override suspend fun getSingle(id: Int): Genres = db.genresTable.getSingle(id)

    override suspend fun getList(): List<Genres> = db.genresTable.getList()

    override suspend fun insertList(genres: List<Genres>): List<Long> = db.genresTable.insertList(genres)

    override suspend fun insertSingle(genres: Genres): Long = db.genresTable.insertSingle(genres)

    override suspend fun deleteAll(): Int = db.genresTable.deleteAll()

    override suspend fun deleteSingle(id: Int): Int = db.genresTable.deleteSingle(id)
}