package com.aplus.common.utils

import com.aplus.domain.model.Genres
import javax.inject.Inject

class MoviesHelper @Inject constructor() {

    fun limitOverview(data: String): String {
        return if (data.length > 100) {
            var overview = data.substring(0, 100)
            if (overview[overview.length - 1] != '.') {
                overview = "$overview..."
            }
            overview
        } else {
            data
        }
    }

    fun convertGenres(data: List<Int>, listGenres: List<Genres>): String {
        var genres = ""
        return if (data.isNotEmpty()) {
            data.forEach {
                val item = listGenres.filter { x -> x.id == it }
                genres += if (item.isNotEmpty()) {
                    "${item[0].name}, "
                } else {
                    "${it}, "
                }
            }
            if (genres.length > 2) {
                genres.substring(0, genres.length - 2)
            } else {
                genres
            }
        } else {
            genres
        }
    }

}