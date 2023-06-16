package com.aplus.data.datasource.remote

import com.aplus.core.constants.KeyConstant
import com.aplus.domain.model.GenresResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresMoviesApi {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") api: String = KeyConstant.API_KEY
    ): Response<GenresResponse>

}