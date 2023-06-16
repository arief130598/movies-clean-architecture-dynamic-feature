package com.aplus.domain.usecases.remote.searchmovies

import javax.inject.Inject

data class SearchMoviesUseCases @Inject constructor (
    val getSearchMovies: GetSearchMovies
)