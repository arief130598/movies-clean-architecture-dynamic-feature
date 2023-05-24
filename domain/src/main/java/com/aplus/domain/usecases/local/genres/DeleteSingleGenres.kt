package com.aplus.domain.usecases.local.genres

import com.aplus.domain.repository.local.GenresRepository
import javax.inject.Inject

class DeleteSingleGenres @Inject constructor(
    private val repository: GenresRepository
) {
    suspend operator fun invoke(id: Int) : Int = repository.deleteSingle(id)
}