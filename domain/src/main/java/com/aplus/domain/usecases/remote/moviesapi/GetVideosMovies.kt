package com.aplus.domain.usecases.remote.moviesapi

import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.VideosResponse
import com.aplus.domain.repository.remote.MoviesApiRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetVideosMovies @Inject constructor(
    private val repository: MoviesApiRepository
) {
    suspend operator fun invoke(movie_id: Int) : Flow<ResponseResult<VideosResponse>> =
        repository.getVideos(movie_id)
}