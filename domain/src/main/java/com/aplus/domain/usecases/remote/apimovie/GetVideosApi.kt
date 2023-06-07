package com.aplus.domain.usecases.remote.apimovie

import com.aplus.domain.model.MoviesVideosResponse
import com.aplus.domain.repository.remote.ApiMovieRepository
import javax.inject.Inject
import retrofit2.Response

class GetVideosApi @Inject constructor(
    private val repository: ApiMovieRepository
) {
    suspend operator fun invoke(movie_id: Int) : Response<MoviesVideosResponse> = repository.getVideos(movie_id)
}