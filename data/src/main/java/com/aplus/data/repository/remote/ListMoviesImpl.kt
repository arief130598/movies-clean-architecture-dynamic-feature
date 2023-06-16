package com.aplus.data.repository.remote

import com.aplus.data.datasource.remote.ListMoviesApi
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.repository.remote.ListMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class ListMoviesImpl @Inject constructor(private val listMoviesApi: ListMoviesApi) :
    ListMoviesRepository {

    override suspend fun getPopular(page: Int): Flow<Response<MoviesResponse>> = flow {
        emit(listMoviesApi.getPopular(page = page))
    }

    override suspend fun getNowPlaying(page: Int): Flow<Response<MoviesResponse>> = flow {
        emit(listMoviesApi.getNowPlaying(page = page))
    }

    override suspend fun getUpcoming(page: Int): Flow<Response<MoviesResponse>> = flow {
        emit(listMoviesApi.getUpcoming(page = page))
    }

    override suspend fun getTopRated(page: Int): Flow<Response<MoviesResponse>> = flow {
        emit(listMoviesApi.getUpcoming(page = page))
    }
}