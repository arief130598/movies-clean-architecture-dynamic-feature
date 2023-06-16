package com.aplus.domain.repository.remote

import com.aplus.domain.model.MoviesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface DiscoverMoviesRepository {

    suspend fun getDiscoverMovies(page: Int, genres: String): Flow<Response<MoviesResponse>>

}