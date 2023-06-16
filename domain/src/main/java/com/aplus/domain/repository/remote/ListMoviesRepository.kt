package com.aplus.domain.repository.remote

import com.aplus.domain.model.MoviesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ListMoviesRepository {

    suspend fun getPopular(page: Int): Flow<Response<MoviesResponse>>

    suspend fun getNowPlaying(page: Int): Flow<Response<MoviesResponse>>

    suspend fun getUpcoming(page: Int): Flow<Response<MoviesResponse>>

    suspend fun getTopRated(page: Int): Flow<Response<MoviesResponse>>

}