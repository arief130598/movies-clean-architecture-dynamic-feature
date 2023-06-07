package com.aplus.domain.model

data class Reviews(
    var author: String = "",
    var author_details: Authors,
    var content: String = "",
    var updated_at: String = ""
)
