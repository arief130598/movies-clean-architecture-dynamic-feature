package com.aplus.domain.usecases.local.movies

import com.aplus.domain.repository.local.MoviesRepository
import javax.inject.Inject

class DeleteSingleMovies @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(id: Int) : Int = repository.deleteSingle(id)
}