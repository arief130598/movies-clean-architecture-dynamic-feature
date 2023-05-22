package com.aplus.domain.repository.remote

import com.aplus.domain.model.GenresResponse
import com.aplus.domain.model.MoviesResponse
import retrofit2.Response

interface ApiMovieRepository {
    suspend fun getGenres(api_key: String): Response<GenresResponse>

    suspend fun getPopular(api_key: String, page: Int): Response<MoviesResponse>

    suspend fun getNowPlaying(api_key: String, page: Int): Response<MoviesResponse>

    suspend fun getUpcoming(api_key: String, page: Int): Response<MoviesResponse>

    suspend fun getSearch(
        api_key: String,
        query: String,
        page: Int
    ): Response<MoviesResponse>

    suspend fun getSimilar(
        movie_id: Int,
        api_key: String,
        page: Int
    ): Response<MoviesResponse>
}