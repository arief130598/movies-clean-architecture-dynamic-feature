package com.aplus.domain.repository.remote

import com.aplus.domain.model.MoviesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SearchMoviesRepository {

    suspend fun getSearch(
        query: String,
        page: Int
    ): Flow<Response<MoviesResponse>>

}