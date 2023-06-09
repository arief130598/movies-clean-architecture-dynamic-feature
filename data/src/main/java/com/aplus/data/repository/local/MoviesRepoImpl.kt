package com.aplus.data.repository.local

import com.aplus.data.datasource.local.MoviesTable
import com.aplus.domain.model.Movies
import com.aplus.domain.repository.local.MoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 *
 * Repository to call from Database -> Genres Table <br/>
 * Repository name must be same from Movies DAO
 *
 */
class MoviesRepoImpl @Inject constructor(private val moviesTable: MoviesTable) : MoviesRepository {

    override suspend fun getSingle(id: Int): Movies = moviesTable.getSingle(id)

    override fun getList(): Flow<List<Movies>> = moviesTable.getList()

    override suspend fun insertList(movies: List<Movies>): List<Long> = moviesTable.insertList(movies)

    override suspend fun insertSingle(movies: Movies): Long = moviesTable.insertSingle(movies)

    override suspend fun deleteAll(): Int = moviesTable.deleteAll()

    override suspend fun deleteSingle(id: Int): Int = moviesTable.deleteSingle(id)
}