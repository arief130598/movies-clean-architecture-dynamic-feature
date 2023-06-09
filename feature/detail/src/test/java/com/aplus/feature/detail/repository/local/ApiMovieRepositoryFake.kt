package com.aplus.feature.detail.repository.local

import com.aplus.domain.model.Authors
import com.aplus.domain.model.Genres
import com.aplus.domain.model.GenresResponse
import com.aplus.domain.model.Movies
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.model.Reviews
import com.aplus.domain.model.ReviewsResponse
import com.aplus.domain.model.Videos
import com.aplus.domain.model.VideosResponse
import com.aplus.domain.repository.remote.ApiMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class ApiMovieRepositoryFake : ApiMovieRepository {
    var dataGenres: Response<GenresResponse> = Response.success(
        GenresResponse(
            genres = listOf(
                Genres(1, "Action"),
                Genres(2, "Horror"),
                Genres(3, "Science"),
                Genres(4, "Romance")
            )
        )
    )
    var listMovies: Response<MoviesResponse> = Response.success(
        MoviesResponse(
            page = 1,
            results = listOf(
                Movies(
                    false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663712, "en",
                    "Terrifier 1", "After being resurrected by a sinister entity, Art the Clown returns to " +
                            "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                            "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
                    9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
                    246
                ),
                Movies(
                    false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663713, "en",
                    "Terrifier 2", "After being resurrected by a sinister entity, Art the Clown returns to " +
                            "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                            "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
                    9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
                    246
                )
            ),
            total_pages = 1,
            total_results = 2
        )
    )
    var dataVideos: Response<VideosResponse> = Response.success(
        VideosResponse(
            id = 1,
            results = listOf(
                Videos(name = "Official Trailer", key = "12345"),
                Videos(name = "Official Trailer", key = "123456"),
                Videos(name = "Official Trailer", key = "123457"),
                Videos(name = "Official Trailer", key = "123458"),
                Videos(name = "Official Trailer", key = "123459"),
            )
        )
    )

    var dataReviews: Response<ReviewsResponse> = Response.success(
        ReviewsResponse(
            id = 1,
            page = 1,
            results = listOf(
                Reviews(author = "abc", author_details = Authors(avatar_path = "abc", rating = 1), content = "abc"),
                Reviews(author = "abcd", author_details = Authors(avatar_path = "abcd", rating = 1), content = "abc"),
                Reviews(author = "abce", author_details = Authors(avatar_path = "abce", rating = 1), content = "abce"),
                Reviews(author = "abcf", author_details = Authors(avatar_path = "abcf", rating = 1), content = "abcf"),
                Reviews(author = "abcg", author_details = Authors(avatar_path = "abcg", rating = 1), content = "abcg"),
            )
        )
    )

    override suspend fun getGenres(): Flow<Response<GenresResponse>> {
        return flow { emit(dataGenres) }
    }

    override suspend fun getMovies(page: Int, genres: String): Flow<Response<MoviesResponse>> {
        return flow { emit(listMovies) }
    }

    override suspend fun getPopular(page: Int): Flow<Response<MoviesResponse>> {
        return flow { emit(listMovies) }
    }

    override suspend fun getNowPlaying(page: Int): Flow<Response<MoviesResponse>> {
        return flow { emit(listMovies) }
    }

    override suspend fun getUpcoming(page: Int): Flow<Response<MoviesResponse>> {
        return flow { emit(listMovies) }
    }

    override suspend fun getSearch(query: String, page: Int): Flow<Response<MoviesResponse>> {
        return flow { emit(listMovies) }
    }

    override suspend fun getSimilar(movie_id: Int, page: Int): Flow<Response<MoviesResponse>> {
        return flow { emit(listMovies) }
    }

    override suspend fun getVideos(movie_id: Int): Flow<Response<VideosResponse>> {
        return flow { emit(dataVideos) }
    }

    override suspend fun getReviews(movie_id: Int, page: Int): Flow<Response<ReviewsResponse>> {
        return flow { emit(dataReviews) }
    }
}