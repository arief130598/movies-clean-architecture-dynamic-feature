package com.aplus.data.repository.remote

import com.aplus.data.datasource.remote.SearchMoviesApi
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.repository.remote.SearchMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class SearchMoviesImpl @Inject constructor(private val searchMoviesApi: SearchMoviesApi) : SearchMoviesRepository {

    override suspend fun getSearch(
        query: String,
        page: Int
    ): Flow<Response<MoviesResponse>> = flow {
        emit(searchMoviesApi.getSearch(query = query, page = page))
    }
}