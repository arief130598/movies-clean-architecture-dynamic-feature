package com.aplus.domain.repository.remote

import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.model.ReviewsResponse
import com.aplus.domain.model.VideosResponse
import kotlinx.coroutines.flow.Flow

interface MoviesApiRepository {
    suspend fun getSimilar(
        movie_id: Int,
        page: Int
    ): Flow<ResponseResult<MoviesResponse>>

    suspend fun getVideos(
        movie_id: Int
    ): Flow<ResponseResult<VideosResponse>>

    suspend fun getReviews(
        movie_id: Int,
        page: Int
    ): Flow<ResponseResult<ReviewsResponse>>
}