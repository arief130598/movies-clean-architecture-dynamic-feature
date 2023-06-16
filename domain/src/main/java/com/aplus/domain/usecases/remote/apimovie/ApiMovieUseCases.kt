package com.aplus.domain.usecases.remote.apimovie

import javax.inject.Inject

data class ApiMovieUseCases @Inject constructor (
    val getGenresApi: GetGenresApi,
    val getMoviesApi: GetMoviesApi,
    val getNowPlayingApi: GetNowPlayingApi,
    val getPopularApi: GetPopularApi,
    val getSearchApi: GetSearchApi,
    val getSimilarApi: GetSimilarApi,
    val getUpcomingApi: GetUpcomingApi,
    val getVideosApi: GetVideosApi,
    val getReviewsApi: GetReviewsApi
)