package com.aplus.domain.usecases.remote.apimovie

import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.repository.remote.ApiMovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetSimilarApi @Inject constructor(
    private val repository: ApiMovieRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int) : Flow<Response<MoviesResponse>> =
        repository.getSimilar(movieId, page)
}