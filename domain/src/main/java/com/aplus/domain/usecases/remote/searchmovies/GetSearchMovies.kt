package com.aplus.domain.usecases.remote.searchmovies

import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.repository.remote.SearchMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSearchMovies @Inject constructor(
    private val repository: SearchMoviesRepository
) {
    suspend operator fun invoke(query: String, page: Int) : Flow<ResponseResult<MoviesResponse>> =
        repository.getSearch(query, page)
}