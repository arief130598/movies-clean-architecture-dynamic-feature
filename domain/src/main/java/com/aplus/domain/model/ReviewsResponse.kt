package com.aplus.domain.model

data class ReviewsResponse(
    var id: Int = 0,
    var page: Int = 0,
    var results: List<Reviews> = listOf()
)
