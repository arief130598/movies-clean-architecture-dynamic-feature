package com.aplus.domain.usecases.remote.genresmovies

import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.GenresResponse
import com.aplus.domain.repository.remote.GenresMoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetGenresMovies @Inject constructor(
    private val repository: GenresMoviesRepository
) {
    suspend operator fun invoke() : Flow<ResponseResult<GenresResponse>> = repository.getGenres()
}