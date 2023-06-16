package com.aplus.data.repository.remote

import com.aplus.core.utils.ResponseResult
import com.aplus.core.utils.validateResponse
import com.aplus.data.datasource.remote.ListMoviesApi
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.repository.remote.ListMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ListMoviesImpl @Inject constructor(private val listMoviesApi: ListMoviesApi) :
    ListMoviesRepository {

    override suspend fun getPopular(page: Int): Flow<ResponseResult<MoviesResponse>> = flow {
        emit(listMoviesApi.getPopular(page = page).validateResponse())
    }

    override suspend fun getNowPlaying(page: Int): Flow<ResponseResult<MoviesResponse>> = flow {
        emit(listMoviesApi.getNowPlaying(page = page).validateResponse())
    }

    override suspend fun getUpcoming(page: Int): Flow<ResponseResult<MoviesResponse>> = flow {
        emit(listMoviesApi.getUpcoming(page = page).validateResponse())
    }

    override suspend fun getTopRated(page: Int): Flow<ResponseResult<MoviesResponse>> = flow {
        emit(listMoviesApi.getUpcoming(page = page).validateResponse())
    }
}