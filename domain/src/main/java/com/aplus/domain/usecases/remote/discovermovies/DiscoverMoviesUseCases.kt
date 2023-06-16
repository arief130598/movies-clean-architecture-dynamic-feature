package com.aplus.domain.usecases.remote.discovermovies

import javax.inject.Inject

data class DiscoverMoviesUseCases @Inject constructor (
    val getDiscoverMovies: GetDiscoverMovies
)