package com.aplus.data.repository.local

import com.aplus.data.di.MovieDatabase
import com.aplus.domain.model.Movies
import com.aplus.domain.repository.local.MoviesRepository
import javax.inject.Inject

/**
 *
 * Repository to call from Database -> Genres Table <br/>
 * Repository name must be same from Movies DAO
 *
 */
class MoviesRepoImpl @Inject constructor(private val db: MovieDatabase) : MoviesRepository {

    override suspend fun getSingle(id: Int): Movies = db.moviesTable.getSingle(id)

    override suspend fun getList(): List<Movies> = db.moviesTable.getList()

    override suspend fun insertList(movies: List<Movies>): List<Long> = db.moviesTable.insertList(movies)

    override suspend fun insertSingle(movies: Movies): Long = db.moviesTable.insertSingle(movies)

    override suspend fun deleteAll(): Int = db.moviesTable.deleteAll()

    override suspend fun deleteSingle(id: Int): Int = db.moviesTable.deleteSingle(id)
}