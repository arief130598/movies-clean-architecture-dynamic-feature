package com.aplus.domain.usecases.remote.apimovie

import com.aplus.domain.model.GenresResponse
import com.aplus.domain.repository.remote.ApiMovieRepository
import javax.inject.Inject
import retrofit2.Response

class GetGenresApi @Inject constructor(
    private val repository: ApiMovieRepository
) {
    suspend operator fun invoke() : Response<GenresResponse> = repository.getGenres()
}