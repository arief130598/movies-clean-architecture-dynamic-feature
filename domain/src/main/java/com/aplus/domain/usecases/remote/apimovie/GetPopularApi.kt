package com.aplus.domain.usecases.remote.apimovie

import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.repository.remote.ApiMovieRepository
import javax.inject.Inject
import retrofit2.Response

class GetPopularApi @Inject constructor(
    private val repository: ApiMovieRepository
) {
    suspend operator fun invoke(page: Int) : Response<MoviesResponse> = repository.getPopular(page)
}