package com.aplus.domain.usecases.local.movies

import com.aplus.domain.model.Movies
import com.aplus.domain.repository.local.MoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetListMovies @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke() : Flow<List<Movies>> = repository.getList()
}