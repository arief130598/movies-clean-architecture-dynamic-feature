package com.aplus.domain.repository.remote

import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface DiscoverMoviesRepository {

    suspend fun getDiscoverMovies(page: Int, genres: String): Flow<ResponseResult<MoviesResponse>>

}