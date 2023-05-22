package com.aplus.domain.usecases.remote.apimovie

import com.aplus.domain.model.GenresResponse
import com.aplus.domain.repository.remote.ApiMovieRepository
import javax.inject.Inject
import retrofit2.Response

class GetGenres @Inject constructor(
    private val repository: ApiMovieRepository
) {
    suspend operator fun invoke(apiKey: String) : Response<GenresResponse> = repository.getGenres(apiKey)
}