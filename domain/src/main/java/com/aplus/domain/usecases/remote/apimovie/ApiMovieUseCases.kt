package com.aplus.domain.usecases.remote.apimovie

data class ApiMovieUseCases (
    val getGenresApi: GetGenresApi,
    val getNowPlayingApi: GetNowPlayingApi,
    val getPopularApi: GetPopularApi,
    val getSearchApi: GetSearchApi,
    val getSimilarApi: GetSimilarApi,
    val getUpcoming: GetUpcomingApi
)