package com.aplus.domain.usecases.remote.listmovies

import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.repository.remote.ListMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTopRatedMovies @Inject constructor(
    private val repository: ListMoviesRepository
) {
    suspend operator fun invoke(page: Int) : Flow<ResponseResult<MoviesResponse>> =
        repository.getUpcoming(page)
}