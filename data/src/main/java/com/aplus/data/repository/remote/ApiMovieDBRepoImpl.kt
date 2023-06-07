package com.aplus.data.repository.remote

import com.aplus.data.datasource.remote.ApiMovieDB
import com.aplus.domain.model.GenresResponse
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.model.ReviewsResponse
import com.aplus.domain.model.VideosResponse
import com.aplus.domain.repository.remote.ApiMovieRepository
import javax.inject.Inject
import retrofit2.Response

/**
 *
 * Repository to call Movie DB from its interface <br/>
 * Repository name must be same from interface function
 *
 */
class ApiMovieDBRepoImpl @Inject constructor(private val apiMovieDB: ApiMovieDB) :
    ApiMovieRepository {

    override suspend fun getGenres(): Response<GenresResponse> = apiMovieDB.getGenres()

    override suspend fun getMovies(page: Int, genres: String): Response<MoviesResponse> =
        apiMovieDB.getMovies(page = page, genres = genres)

    override suspend fun getPopular(page: Int): Response<MoviesResponse> =
        apiMovieDB.getPopular(page = page)

    override suspend fun getNowPlaying(page: Int): Response<MoviesResponse> =
        apiMovieDB.getNowPlaying(page = page)

    override suspend fun getUpcoming(page: Int): Response<MoviesResponse> =
        apiMovieDB.getUpcoming(page = page)

    override suspend fun getSearch(
        query: String,
        page: Int
    ): Response<MoviesResponse> = apiMovieDB.getSearch(
        query = query,
        page = page
    )

    override suspend fun getSimilar(
        movie_id: Int,
        page: Int
    ): Response<MoviesResponse> = apiMovieDB.getSimilar(
        movie_id = movie_id,
        page = page
    )

    override suspend fun getVideos(
        movie_id: Int
    ): Response<VideosResponse> = apiMovieDB.getVideos(
        movie_id = movie_id
    )

    override suspend fun getReviews(
        movie_id: Int,
        page: Int
    ): Response<ReviewsResponse> = apiMovieDB.getReviews(
        movie_id = movie_id,
        page = page
    )
}