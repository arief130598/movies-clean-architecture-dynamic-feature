package com.aplus.data.repository.remote

import com.aplus.data.datasource.remote.ApiMovieDB
import com.aplus.domain.model.GenresResponse
import com.aplus.domain.model.MoviesResponse
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

    override suspend fun getGenres(api_key: String): Response<GenresResponse> = apiMovieDB.getGenres(api_key)

    override suspend fun getPopular(api_key: String, page:Int): Response<MoviesResponse> = apiMovieDB.getPopular(api_key, page)

    override suspend fun getNowPlaying(api_key: String, page:Int): Response<MoviesResponse> = apiMovieDB.getNowPlaying(api_key, page)

    override suspend fun getUpcoming(api_key: String, page:Int): Response<MoviesResponse> = apiMovieDB.getUpcoming(api_key, page)

    override suspend fun getSearch(api_key: String, query:String, page:Int): Response<MoviesResponse> = apiMovieDB.getSearch(api_key, query, page)

    override suspend fun getSimilar(movie_id: Int, api_key: String, page:Int): Response<MoviesResponse> = apiMovieDB.getSimilar(movie_id, api_key, page)
}