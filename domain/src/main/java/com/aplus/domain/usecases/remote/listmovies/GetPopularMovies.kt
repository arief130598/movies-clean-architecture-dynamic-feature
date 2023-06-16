package com.aplus.domain.usecases.remote.listmovies

import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.repository.remote.ListMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetPopularMovies @Inject constructor(
    private val repository: ListMoviesRepository
) {
    suspend operator fun invoke(page: Int) : Flow<Response<MoviesResponse>> =
        repository.getPopular(page)
}