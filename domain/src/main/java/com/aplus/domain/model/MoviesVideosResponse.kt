package com.aplus.domain.model

data class MoviesVideosResponse(
    var id: Int = 0,
    var results : List<MoviesVideos> = listOf()
)
