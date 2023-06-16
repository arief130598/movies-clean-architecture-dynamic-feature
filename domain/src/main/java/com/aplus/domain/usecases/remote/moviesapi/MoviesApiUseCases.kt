package com.aplus.domain.usecases.remote.moviesapi

import javax.inject.Inject


data class MoviesApiUseCases @Inject constructor (
    val getReviewsMovies: GetReviewsMovies,
    val getSimilarMovies: GetSimilarMovies,
    val getVideosMovies: GetVideosMovies
)