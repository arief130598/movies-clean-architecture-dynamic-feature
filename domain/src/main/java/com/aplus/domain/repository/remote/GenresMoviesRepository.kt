package com.aplus.domain.repository.remote

import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.GenresResponse
import kotlinx.coroutines.flow.Flow

interface GenresMoviesRepository {

    suspend fun getGenres(): Flow<ResponseResult<GenresResponse>>

}