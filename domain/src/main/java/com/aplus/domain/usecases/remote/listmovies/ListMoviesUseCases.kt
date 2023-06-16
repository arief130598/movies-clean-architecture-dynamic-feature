package com.aplus.domain.usecases.remote.listmovies

import javax.inject.Inject

data class ListMoviesUseCases @Inject constructor (
    val getNowPlayingMovies: GetNowPlayingMovies,
    val getPopularMovies: GetPopularMovies,
    val getTopRatedMovies: GetTopRatedMovies,
    val getUpcomingMovies: GetUpcomingMovies
)