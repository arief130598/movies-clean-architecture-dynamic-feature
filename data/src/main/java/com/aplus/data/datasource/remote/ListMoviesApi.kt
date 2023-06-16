package com.aplus.data.datasource.remote

import com.aplus.core.constants.KeyConstant
import com.aplus.domain.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ListMoviesApi {

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") api: String = KeyConstant.API_KEY,
        @Query("page") page: Int,
    ): Response<MoviesResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") api: String = KeyConstant.API_KEY,
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") api: String = KeyConstant.API_KEY,
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") api: String = KeyConstant.API_KEY,
        @Query("page") page: Int
    ): Response<MoviesResponse>
}