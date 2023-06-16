package com.aplus.domain.repository.remote

import com.aplus.domain.model.GenresResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GenresMoviesRepository {

    suspend fun getGenres(): Flow<Response<GenresResponse>>

}