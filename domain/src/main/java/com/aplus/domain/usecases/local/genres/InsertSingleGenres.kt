package com.aplus.domain.usecases.local.genres

import com.aplus.domain.model.Genres
import com.aplus.domain.repository.local.GenresRepository
import javax.inject.Inject

class InsertSingleGenres @Inject constructor(
    private val repository: GenresRepository
) {
    suspend operator fun invoke(genres: Genres) : Long = repository.insertSingle(genres)
}