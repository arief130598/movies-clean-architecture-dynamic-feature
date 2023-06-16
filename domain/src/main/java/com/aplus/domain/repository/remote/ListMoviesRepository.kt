package com.aplus.domain.repository.remote

import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface ListMoviesRepository {

    suspend fun getPopular(page: Int): Flow<ResponseResult<MoviesResponse>>

    suspend fun getNowPlaying(page: Int): Flow<ResponseResult<MoviesResponse>>

    suspend fun getUpcoming(page: Int): Flow<ResponseResult<MoviesResponse>>

    suspend fun getTopRated(page: Int): Flow<ResponseResult<MoviesResponse>>

}