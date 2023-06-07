package com.aplus.domain.usecases.remote.apimovie

data class ApiMovieUseCases (
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