package com.aplus.domain.usecases.local.genres

import com.aplus.domain.model.Genres
import com.aplus.domain.repository.local.GenresRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetListGenres @Inject constructor(
    private val repository: GenresRepository
) {
    operator fun invoke() : Flow<List<Genres>> = repository.getList()
}