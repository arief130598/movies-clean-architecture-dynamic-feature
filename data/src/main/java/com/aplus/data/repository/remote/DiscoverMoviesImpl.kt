package com.aplus.data.repository.remote

import com.aplus.data.datasource.remote.DiscoverMoviesApi
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.repository.remote.DiscoverMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class DiscoverMoviesImpl @Inject constructor(private val discoverMoviesApi: DiscoverMoviesApi) :
    DiscoverMoviesRepository {

    override suspend fun getDiscoverMovies(
        page: Int,
        genres: String
    ): Flow<Response<MoviesResponse>> = flow {
        emit(discoverMoviesApi.getDiscoverMovies(page = page, genres = genres))
    }
}