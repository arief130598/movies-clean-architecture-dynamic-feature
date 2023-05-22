package com.aplus.domain.usecases.local.genres

import com.aplus.domain.model.Genres
import com.aplus.domain.repository.local.GenresRepository
import javax.inject.Inject

class GetSingle @Inject constructor(
    private val repository: GenresRepository
) {
    suspend operator fun invoke(id: Int) : Genres = repository.getSingle(id)
}