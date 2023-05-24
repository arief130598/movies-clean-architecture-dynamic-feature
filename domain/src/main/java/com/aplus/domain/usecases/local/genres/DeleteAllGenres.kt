package com.aplus.domain.usecases.local.genres

import com.aplus.domain.repository.local.GenresRepository
import javax.inject.Inject

class DeleteAllGenres @Inject constructor(
    private val repository: GenresRepository
) {
    suspend operator fun invoke() : Int = repository.deleteAll()
}