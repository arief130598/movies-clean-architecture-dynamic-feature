package com.aplus.data.repository.remote

import com.aplus.data.datasource.remote.ApiMovieDB
import com.aplus.domain.model.GenresResponse
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.model.MoviesVideosResponse
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
    ): Response<MoviesVideosResponse> = apiMovieDB.getVideos(
        movie_id = movie_id
    )
}