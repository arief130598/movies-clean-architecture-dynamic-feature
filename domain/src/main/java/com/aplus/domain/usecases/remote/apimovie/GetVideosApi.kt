package com.aplus.domain.usecases.remote.apimovie

import com.aplus.domain.model.VideosResponse
import com.aplus.domain.repository.remote.ApiMovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetVideosApi @Inject constructor(
    private val repository: ApiMovieRepository
) {
    suspend operator fun invoke(movie_id: Int) : Flow<Response<VideosResponse>> =
        repository.getVideos(movie_id)
}