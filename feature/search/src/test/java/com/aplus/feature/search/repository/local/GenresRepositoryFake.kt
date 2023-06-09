package com.aplus.feature.search.repository.local

import com.aplus.domain.model.Genres
import com.aplus.domain.repository.local.GenresRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GenresRepositoryFake : GenresRepository {

    var dataGenres = listOf(
        Genres(1, "Action"),
        Genres(2, "Horror"),
        Genres(3, "Science"),
        Genres(4, "Romance")
    )

    override suspend fun getSingle(id: Int): Genres {
        TODO("Not yet implemented")
    }

    override fun getList(): Flow<List<Genres>> {
        return flow {
            emit(dataGenres)
        }
    }

    override suspend fun insertList(genres: List<Genres>): List<Long> {
        return listOf(1,2,3,4)
    }

    override suspend fun insertSingle(genres: Genres): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSingle(id: Int): Int {
        TODO("Not yet implemented")
    }
}