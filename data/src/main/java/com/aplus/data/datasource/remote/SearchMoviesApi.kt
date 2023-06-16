package com.aplus.data.datasource.remote

import com.aplus.core.constants.KeyConstant
import com.aplus.domain.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchMoviesApi {

    @GET("search/movie")
    suspend fun getSearch(
        @Query("api_key") api: String = KeyConstant.API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<MoviesResponse>

}