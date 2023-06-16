package com.aplus.domain.usecases.remote.moviesapi

import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.repository.remote.MoviesApiRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSimilarMovies @Inject constructor(
    private val repository: MoviesApiRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int) : Flow<ResponseResult<MoviesResponse>> =
        repository.getSimilar(movieId, page)
}