package com.aplus.data.di

import android.content.Context
import com.aplus.data.repository.local.GenresRepoImpl
import com.aplus.data.repository.local.MoviesRepoImpl
import com.aplus.domain.repository.local.GenresRepository
import com.aplus.domain.repository.local.MoviesRepository
import com.aplus.domain.usecases.local.genres.DeleteAllGenres
import com.aplus.domain.usecases.local.genres.DeleteSingleGenres
import com.aplus.domain.usecases.local.genres.GenresUseCases
import com.aplus.domain.usecases.local.genres.GetListGenres
import com.aplus.domain.usecases.local.genres.GetSingleGenres
import com.aplus.domain.usecases.local.genres.InsertListGenres
import com.aplus.domain.usecases.local.genres.InsertSingleGenres
import com.aplus.domain.usecases.local.movies.DeleteAllMovies
import com.aplus.domain.usecases.local.movies.DeleteSingleMovies
import com.aplus.domain.usecases.local.movies.GetListMovies
import com.aplus.domain.usecases.local.movies.GetSingleMovies
import com.aplus.domain.usecases.local.movies.InsertListMovies
import com.aplus.domain.usecases.local.movies.InsertSingleMovies
import com.aplus.domain.usecases.local.movies.MoviesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase = MovieDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideMoviesRepository(movieDatabase: MovieDatabase): MoviesRepository = MoviesRepoImpl(movieDatabase.moviesTable)

    @Provides
    @Singleton
    fun provideGenresRepository(movieDatabase: MovieDatabase): GenresRepository = GenresRepoImpl(movieDatabase.genresTable)

    @Provides
    @Singleton
    fun provideMoviesUseCases(moviesRepository: MoviesRepository): MoviesUseCases = MoviesUseCases(
        deleteAllMovies = DeleteAllMovies(moviesRepository),
        deleteSingleMovies = DeleteSingleMovies(moviesRepository),
        getListMovies = GetListMovies(moviesRepository),
        getSingleMovies = GetSingleMovies(moviesRepository),
        insertListMovies = InsertListMovies(moviesRepository),
        insertSingleMovies = InsertSingleMovies(moviesRepository)
    )

    @Provides
    @Singleton
    fun provideGenresUseCases(genresRepository: GenresRepository): GenresUseCases = GenresUseCases(
        deleteAllGenres = DeleteAllGenres(genresRepository),
        deleteSingleGenres = DeleteSingleGenres(genresRepository),
        getListGenres = GetListGenres(genresRepository),
        getSingleGenres = GetSingleGenres(genresRepository),
        insertListGenres = InsertListGenres(genresRepository),
        insertSingleGenres = InsertSingleGenres(genresRepository)
    )
}