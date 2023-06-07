package com.aplus.domain.repository.remote

import com.aplus.domain.model.GenresResponse
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.model.MoviesVideosResponse
import retrofit2.Response

interface ApiMovieRepository {
    suspend fun getGenres(): Response<GenresResponse>

    suspend fun getPopular(page: Int): Response<MoviesResponse>

    suspend fun getNowPlaying(page: Int): Response<MoviesResponse>

    suspend fun getUpcoming(page: Int): Response<MoviesResponse>

    suspend fun getSearch(
        query: String,
        page: Int
    ): Response<MoviesResponse>

    suspend fun getSimilar(
        movie_id: Int,
        page: Int
    ): Response<MoviesResponse>

    suspend fun getVideos(
        movie_id: Int
    ): Response<MoviesVideosResponse>
}