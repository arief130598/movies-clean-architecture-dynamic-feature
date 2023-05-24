package com.aplus.domain.usecases.local.movies

import com.aplus.domain.model.Movies
import com.aplus.domain.repository.local.MoviesRepository
import javax.inject.Inject

class GetSingleMovies @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(id: Int) : Movies = repository.getSingle(id)
}