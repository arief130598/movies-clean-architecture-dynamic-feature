package com.aplus.data.di

import android.app.Application
import com.aplus.core.BuildConfig
import com.aplus.core.utils.NetworkHelper
import com.aplus.data.datasource.remote.ApiMovieDB
import com.aplus.data.datasource.remote.DiscoverMoviesApi
import com.aplus.data.datasource.remote.GenresMoviesApi
import com.aplus.data.datasource.remote.ListMoviesApi
import com.aplus.data.datasource.remote.MoviesApi
import com.aplus.data.datasource.remote.SearchMoviesApi
import com.aplus.data.repository.remote.ApiMovieDBRepoImpl
import com.aplus.data.repository.remote.DiscoverMoviesImpl
import com.aplus.data.repository.remote.GenresMoviesImpl
import com.aplus.data.repository.remote.ListMoviesImpl
import com.aplus.data.repository.remote.MoviesApiImpl
import com.aplus.data.repository.remote.SearchMoviesImpl
import com.aplus.domain.repository.remote.ApiMovieRepository
import com.aplus.domain.repository.remote.DiscoverMoviesRepository
import com.aplus.domain.repository.remote.GenresMoviesRepository
import com.aplus.domain.repository.remote.ListMoviesRepository
import com.aplus.domain.repository.remote.MoviesApiRepository
import com.aplus.domain.repository.remote.SearchMoviesRepository
import com.aplus.domain.usecases.remote.discovermovies.GetDiscoverMovies
import com.aplus.domain.usecases.remote.discovermovies.DiscoverMoviesUseCases
import com.aplus.domain.usecases.remote.genresmovies.GenresMoviesUseCases
import com.aplus.domain.usecases.remote.genresmovies.GetGenresMovies
import com.aplus.domain.usecases.remote.listmovies.GetNowPlayingMovies
import com.aplus.domain.usecases.remote.listmovies.GetPopularMovies
import com.aplus.domain.usecases.remote.listmovies.GetTopRatedMovies
import com.aplus.domain.usecases.remote.listmovies.GetUpcomingMovies
import com.aplus.domain.usecases.remote.listmovies.ListMoviesUseCases
import com.aplus.domain.usecases.remote.moviesapi.GetReviewsMovies
import com.aplus.domain.usecases.remote.moviesapi.GetSimilarMovies
import com.aplus.domain.usecases.remote.moviesapi.GetVideosMovies
import com.aplus.domain.usecases.remote.moviesapi.MoviesApiUseCases
import com.aplus.domain.usecases.remote.searchmovies.GetSearchMovies
import com.aplus.domain.usecases.remote.searchmovies.SearchMoviesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val timeout = 15L
    private val BASE_URL: String = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideNetworkHelper(application: Application) = NetworkHelper(application.applicationContext)

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .callTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiMovieDB =
        retrofit.create(ApiMovieDB::class.java)

    @Provides
    @Singleton
    fun provideDiscoverMoviesApi(retrofit: Retrofit): DiscoverMoviesApi =
        retrofit.create(DiscoverMoviesApi::class.java)

    @Provides
    @Singleton
    fun provideGenresMoviesApi(retrofit: Retrofit): GenresMoviesApi =
        retrofit.create(GenresMoviesApi::class.java)

    @Provides
    @Singleton
    fun provideListMoviesApi(retrofit: Retrofit): ListMoviesApi =
        retrofit.create(ListMoviesApi::class.java)

    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi =
        retrofit.create(MoviesApi::class.java)

    @Provides
    @Singleton
    fun provideSearchMoviesApi(retrofit: Retrofit): SearchMoviesApi =
        retrofit.create(SearchMoviesApi::class.java)

    @Provides
    @Singleton
    fun provideApiRepository(apiMovieDB: ApiMovieDB): ApiMovieRepository =
        ApiMovieDBRepoImpl(apiMovieDB)

    @Provides
    @Singleton
    fun provideDiscoverMoviesRepository(discoverMoviesApi: DiscoverMoviesApi) :
            DiscoverMoviesRepository = DiscoverMoviesImpl(discoverMoviesApi)

    @Provides
    @Singleton
    fun provideGenresMoviesRepository(genresMoviesApi: GenresMoviesApi): GenresMoviesRepository =
        GenresMoviesImpl(genresMoviesApi)

    @Provides
    @Singleton
    fun provideListMoviesRepository(listMoviesApi: ListMoviesApi): ListMoviesRepository =
        ListMoviesImpl(listMoviesApi)

    @Provides
    @Singleton
    fun provideMoviesApiRepository(moviesApi: MoviesApi): MoviesApiRepository =
        MoviesApiImpl(moviesApi)

    @Provides
    @Singleton
    fun provideSearchMoviesRepository(searchMoviesApi: SearchMoviesApi): SearchMoviesRepository =
        SearchMoviesImpl(searchMoviesApi)

    @Provides
    @Singleton
    fun provideDiscoverMoviesUseCases(discoverMoviesRepository: DiscoverMoviesRepository)
            : DiscoverMoviesUseCases = DiscoverMoviesUseCases(
        getDiscoverMovies = GetDiscoverMovies(discoverMoviesRepository)
    )

    @Provides
    @Singleton
    fun provideGenresMoviesUseCases(genresMoviesRepository: GenresMoviesRepository)
            : GenresMoviesUseCases = GenresMoviesUseCases(
        getGenresMovies = GetGenresMovies(genresMoviesRepository)
    )

    @Provides
    @Singleton
    fun provideListMoviesUseCases(listMoviesRepository: ListMoviesRepository)
            : ListMoviesUseCases = ListMoviesUseCases(
        getPopularMovies = GetPopularMovies(listMoviesRepository),
        getNowPlayingMovies = GetNowPlayingMovies(listMoviesRepository),
        getTopRatedMovies = GetTopRatedMovies(listMoviesRepository),
        getUpcomingMovies = GetUpcomingMovies(listMoviesRepository)
    )

    @Provides
    @Singleton
    fun provideMoviesApiUseCases(moviesApiRepository: MoviesApiRepository)
            : MoviesApiUseCases = MoviesApiUseCases(
        getReviewsMovies = GetReviewsMovies(moviesApiRepository),
        getSimilarMovies = GetSimilarMovies(moviesApiRepository),
        getVideosMovies = GetVideosMovies(moviesApiRepository)
    )

    @Provides
    @Singleton
    fun provideSearchUseCases(searchMoviesRepository: SearchMoviesRepository)
            : SearchMoviesUseCases = SearchMoviesUseCases(
        getSearchMovies = GetSearchMovies(searchMoviesRepository)
    )
}