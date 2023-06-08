package com.aplus.common.presentation.viewmodel

import com.aplus.core.utils.DispatcherProvider
import com.aplus.domain.usecases.local.genres.GenresUseCases
import com.aplus.domain.usecases.local.movies.MoviesUseCases
import com.aplus.domain.usecases.remote.apimovie.ApiMovieUseCases

class FakeMovieViewModel(
    apiMovieUseCases: ApiMovieUseCases,
    genresUseCases: GenresUseCases,
    moviesUseCases: MoviesUseCases,
    dispatcher: DispatcherProvider,
) : MovieViewModel(apiMovieUseCases, genresUseCases, moviesUseCases, dispatcher) {

    init {
        getFavorite()
        getGenres()
    }
}