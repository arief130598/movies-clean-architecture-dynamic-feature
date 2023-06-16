package com.aplus.domain.usecases.remote.discovermovies

import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.repository.remote.DiscoverMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetDiscoverMovies @Inject constructor(
    private val repository: DiscoverMoviesRepository
) {
    suspend operator fun invoke(page: Int, genres: String): Flow<ResponseResult<MoviesResponse>> =
        repository.getDiscoverMovies(page, genres)
}