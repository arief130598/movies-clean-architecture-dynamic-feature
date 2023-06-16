package com.aplus.data.datasource.remote

import com.aplus.core.constants.KeyConstant
import com.aplus.domain.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverMoviesApi {

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("api_key") api: String = KeyConstant.API_KEY,
        @Query("page") page: Int,
        @Query("with_genres") genres: String,
    ): Response<MoviesResponse>

}