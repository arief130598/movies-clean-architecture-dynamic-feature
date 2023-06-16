package com.aplus.data.repository.remote

import com.aplus.data.datasource.remote.GenresMoviesApi
import com.aplus.domain.model.GenresResponse
import com.aplus.domain.repository.remote.GenresMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class GenresMoviesImpl @Inject constructor(private val genresMoviesApi: GenresMoviesApi) :
    GenresMoviesRepository {

    override suspend fun getGenres(): Flow<Response<GenresResponse>> = flow {
        emit(genresMoviesApi.getGenres())
    }
}