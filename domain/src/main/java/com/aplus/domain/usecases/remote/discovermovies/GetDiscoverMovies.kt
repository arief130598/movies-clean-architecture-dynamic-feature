package com.aplus.domain.usecases.remote.discovermovies

import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.repository.remote.DiscoverMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetDiscoverMovies @Inject constructor(
    private val repository: DiscoverMoviesRepository
) {
    suspend operator fun invoke(page: Int, genres: String): Flow<Response<MoviesResponse>> =
        repository.getDiscoverMovies(page, genres)
}