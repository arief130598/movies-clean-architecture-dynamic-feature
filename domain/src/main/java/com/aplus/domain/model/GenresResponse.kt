package com.aplus.domain.model

import com.aplus.domain.model.Genres

/**
 *
 * Model class for receiving API to get list of Genres
 *
 * @property genres
 */
data class GenresResponse(
    var genres: List<Genres> = listOf()
)
