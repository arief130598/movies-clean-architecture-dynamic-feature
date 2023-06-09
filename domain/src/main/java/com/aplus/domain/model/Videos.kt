package com.aplus.domain.model

data class Videos(
    var iso_639_1: String = "",
    var iso_3166_1: String = "",
    var name: String = "",
    var key: String = "",
    var site: String = "",
    var size: Int = 0,
    var type: String = "",
    var official: Boolean = false,
    var published_at: String = "",
    var id: String = "",
)
