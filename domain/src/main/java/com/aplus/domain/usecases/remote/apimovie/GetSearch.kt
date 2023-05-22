package com.aplus.domain.usecases.remote.apimovie

import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.repository.remote.ApiMovieRepository
import javax.inject.Inject
import retrofit2.Response

class GetSearch @Inject constructor(
    private val repository: ApiMovieRepository
) {
    suspend operator fun invoke(apiKey: String, query: String, page: Int) : Response<MoviesResponse>
    = repository.getSearch(apiKey, query, page)
}