package com.aplus.data.datasource.remote

import com.aplus.core.constants.KeyConstant
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.model.ReviewsResponse
import com.aplus.domain.model.VideosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilar(
        @Path("movie_id") movie_id: Int,
        @Query("page") page: Int,
        @Query("api_key") api: String = KeyConstant.API_KEY
    ): Response<MoviesResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api: String = KeyConstant.API_KEY
    ): Response<VideosResponse>

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movie_id: Int,
        @Query("page") page: Int,
        @Query("api_key") api: String = KeyConstant.API_KEY
    ): Response<ReviewsResponse>

}