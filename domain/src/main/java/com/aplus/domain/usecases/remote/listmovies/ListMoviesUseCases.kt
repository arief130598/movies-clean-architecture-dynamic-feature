package com.aplus.domain.usecases.remote.listmovies

data class ListMoviesUseCases (
    val getNowPlayingMovies: GetNowPlayingMovies,
    val getPopularMovies: GetPopularMovies,
    val getTopRatedMovies: GetTopRatedMovies,
    val getUpcomingMovies: GetUpcomingMovies
)