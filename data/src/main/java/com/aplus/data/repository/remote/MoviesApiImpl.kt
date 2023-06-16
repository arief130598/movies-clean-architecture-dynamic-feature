package com.aplus.data.repository.remote

import com.aplus.data.datasource.remote.MoviesApi
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.model.ReviewsResponse
import com.aplus.domain.model.VideosResponse
import com.aplus.domain.repository.remote.MoviesApiRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class MoviesApiImpl @Inject constructor(private val moviesApi: MoviesApi) :
    MoviesApiRepository {

    override suspend fun getSimilar(
        movie_id: Int,
        page: Int
    ): Flow<Response<MoviesResponse>> = flow {
        emit(moviesApi.getSimilar(movie_id = movie_id, page = page))
    }

    override suspend fun getVideos(
        movie_id: Int
    ): Flow<Response<VideosResponse>> = flow {
        emit(moviesApi.getVideos(movie_id = movie_id))
    }

    override suspend fun getReviews(
        movie_id: Int,
        page: Int
    ): Flow<Response<ReviewsResponse>> = flow {
        emit(moviesApi.getReviews(movie_id = movie_id, page = page))
    }
}