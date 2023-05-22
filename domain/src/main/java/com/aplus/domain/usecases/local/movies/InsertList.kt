package com.aplus.domain.usecases.local.movies

import com.aplus.domain.model.Movies
import com.aplus.domain.repository.local.MoviesRepository
import javax.inject.Inject

class InsertList @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movies: List<Movies>) : List<Long> = repository.insertList(movies)
}