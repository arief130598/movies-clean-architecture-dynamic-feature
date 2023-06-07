package com.aplus.domain.model

data class VideosResponse(
    var id: Int = 0,
    var results : List<Videos> = listOf()
)
