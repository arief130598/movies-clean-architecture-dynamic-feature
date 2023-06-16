package com.aplus.domain.usecases.remote.genresmovies

import javax.inject.Inject

data class GenresMoviesUseCases @Inject constructor (
    val getGenresMovies: GetGenresMovies
)