package com.aplus.domain.usecases.remote.apimovie

data class ApiMovieUseCases (
    val getGenres: GetGenres,
    val getNowPlaying: GetNowPlaying,
    val getPopular: GetPopular,
    val getSearch: GetSearch,
    val getSimilar: GetSimilar,
    val getUpcoming: GetUpcoming
)