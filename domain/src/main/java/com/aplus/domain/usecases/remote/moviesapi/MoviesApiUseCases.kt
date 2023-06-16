package com.aplus.domain.usecases.remote.moviesapi


data class MoviesApiUseCases (
    val getReviewsMovies: GetReviewsMovies,
    val getSimilarMovies: GetSimilarMovies,
    val getVideosMovies: GetVideosMovies
)