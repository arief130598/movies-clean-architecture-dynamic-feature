package com.aplus.domain.repository.remote

import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface SearchMoviesRepository {

    suspend fun getSearch(
        query: String,
        page: Int
    ): Flow<ResponseResult<MoviesResponse>>

}