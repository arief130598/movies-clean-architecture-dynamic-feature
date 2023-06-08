package com.aplus.feature.favorite.repository.local

import com.aplus.domain.model.Movies
import com.aplus.domain.repository.local.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryFake : MoviesRepository {

    var listMovies: List<Movies> = listOf(
        Movies(
            false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663712, "en",
            "Terrifier 1", "After being resurrected by a sinister entity, Art the Clown returns to " +
                    "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                    "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
            9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
            246
        ),
        Movies(
            false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663713, "en",
            "Terrifier 2", "After being resurrected by a sinister entity, Art the Clown returns to " +
                    "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                    "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
            9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
            246
        )
    )

    override suspend fun getSingle(id: Int): Movies {
        TODO("Not yet implemented")
    }

    override fun getList(): Flow<List<Movies>> {
        return flow {
            emit(listMovies)
        }
    }

    override suspend fun insertList(movies: List<Movies>): List<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun insertSingle(movies: Movies): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSingle(id: Int): Int {
        TODO("Not yet implemented")
    }
}