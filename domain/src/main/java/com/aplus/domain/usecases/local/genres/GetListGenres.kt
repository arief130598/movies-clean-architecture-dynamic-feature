package com.aplus.domain.usecases.local.genres

import com.aplus.domain.model.Genres
import com.aplus.domain.repository.local.GenresRepository
import javax.inject.Inject

class GetListGenres @Inject constructor(
    private val repository: GenresRepository
) {
    suspend operator fun invoke() : List<Genres> = repository.getList()
}