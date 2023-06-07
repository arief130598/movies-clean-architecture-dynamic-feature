package com.aplus.data.di

import android.app.Application
import com.aplus.core.utils.NetworkHelper
import com.aplus.data.datasource.remote.ApiMovieDB
import com.aplus.data.repository.remote.ApiMovieDBRepoImpl
import com.aplus.domain.repository.remote.ApiMovieRepository
import com.aplus.domain.usecases.remote.apimovie.ApiMovieUseCases
import com.aplus.domain.usecases.remote.apimovie.GetGenresApi
import com.aplus.domain.usecases.remote.apimovie.GetNowPlayingApi
import com.aplus.domain.usecases.remote.apimovie.GetPopularApi
import com.aplus.domain.usecases.remote.apimovie.GetReviewsApi
import com.aplus.domain.usecases.remote.apimovie.GetSearchApi
import com.aplus.domain.usecases.remote.apimovie.GetSimilarApi
import com.aplus.domain.usecases.remote.apimovie.GetUpcomingApi
import com.aplus.domain.usecases.remote.apimovie.GetVideosApi
import dagger.Binds
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
    fun provideOkHttpClient() = if (true) {
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
    fun provideApiRepository(apiMovieDB: ApiMovieDB): ApiMovieRepository =
        ApiMovieDBRepoImpl(apiMovieDB)

    @Provides
    @Singleton
    fun provideApiUseCases(apiMovieRepository: ApiMovieRepository): ApiMovieUseCases = ApiMovieUseCases(
            getGenresApi = GetGenresApi(apiMovieRepository),
            getNowPlayingApi = GetNowPlayingApi(apiMovieRepository),
            getPopularApi = GetPopularApi(apiMovieRepository),
            getSearchApi = GetSearchApi(apiMovieRepository),
            getSimilarApi = GetSimilarApi(apiMovieRepository),
            getUpcomingApi = GetUpcomingApi(apiMovieRepository),
            getVideosApi = GetVideosApi(apiMovieRepository),
            getReviewsApi = GetReviewsApi(apiMovieRepository)
        )
}