package com.aplus.data.repository.local

import com.aplus.data.datasource.local.GenresTable
import com.aplus.data.di.MovieDatabase
import com.aplus.domain.model.Genres
import com.aplus.domain.repository.local.GenresRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 *
 * Repository to call from Database -> Genres Table <br/>
 * Repository name must be same from Genres DAO
 *
 */
class GenresRepoImpl @Inject constructor(private val genresTable: GenresTable) : GenresRepository {

    override suspend fun getSingle(id: Int): Genres = genresTable.getSingle(id)

    override fun getList(): Flow<List<Genres>> = genresTable.getList()

    override suspend fun insertList(genres: List<Genres>): List<Long> = genresTable.insertList(genres)

    override suspend fun insertSingle(genres: Genres): Long = genresTable.insertSingle(genres)

    override suspend fun deleteAll(): Int = genresTable.deleteAll()

    override suspend fun deleteSingle(id: Int): Int = genresTable.deleteSingle(id)
}