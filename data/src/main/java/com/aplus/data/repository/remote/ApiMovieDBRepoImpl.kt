package com.aplus.data.repository.remote

import com.aplus.data.datasource.remote.ApiMovieDB
import com.aplus.domain.model.GenresResponse
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.model.ReviewsResponse
import com.aplus.domain.model.VideosResponse
import com.aplus.domain.repository.remote.ApiMovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 *
 * Repository to call Movie DB from its interface <br/>
 * Repository name must be same from interface function
 *
 */
class ApiMovieDBRepoImpl @Inject constructor(private val apiMovieDB: ApiMovieDB) :
    ApiMovieRepository {

    override suspend fun getGenres(): Flow<Response<GenresResponse>> = flow {
        emit(apiMovieDB.getGenres())
    }

    override suspend fun getMovies(page: Int, genres: String): Flow<Response<MoviesResponse>> =
        flow {
            emit(apiMovieDB.getMovies(page = page, genres = genres))
        }

    override suspend fun getPopular(page: Int): Flow<Response<MoviesResponse>> = flow {
        emit(apiMovieDB.getPopular(page = page))
    }

    override suspend fun getNowPlaying(page: Int): Flow<Response<MoviesResponse>> = flow {
        emit(apiMovieDB.getNowPlaying(page = page))
    }

    override suspend fun getUpcoming(page: Int): Flow<Response<MoviesResponse>> = flow {
        emit(apiMovieDB.getUpcoming(page = page))
    }

    override suspend fun getSearch(
        query: String,
        page: Int
    ): Flow<Response<MoviesResponse>> = flow {
        emit(
            apiMovieDB.getSearch(
                query = query,
                page = page
            )
        )
    }

    override suspend fun getSimilar(
        movie_id: Int,
        page: Int
    ): Flow<Response<MoviesResponse>> = flow {
        emit(
            apiMovieDB.getSimilar(
                movie_id = movie_id,
                page = page
            )
        )
    }

    override suspend fun getVideos(
        movie_id: Int
    ): Flow<Response<VideosResponse>> = flow {
        emit(
            apiMovieDB.getVideos(
                movie_id = movie_id
            )
        )
    }

    override suspend fun getReviews(
        movie_id: Int,
        page: Int
    ): Flow<Response<ReviewsResponse>> = flow {
        emit(
            apiMovieDB.getReviews(
                movie_id = movie_id,
                page = page
            )
        )
    }
}