package com.aplus.domain.repository.remote

import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.model.ReviewsResponse
import com.aplus.domain.model.VideosResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MoviesApiRepository {

    suspend fun getSimilar(
        movie_id: Int,
        page: Int
    ): Flow<Response<MoviesResponse>>

    suspend fun getVideos(
        movie_id: Int
    ): Flow<Response<VideosResponse>>

    suspend fun getReviews(
        movie_id: Int,
        page: Int
    ): Flow<Response<ReviewsResponse>>
}