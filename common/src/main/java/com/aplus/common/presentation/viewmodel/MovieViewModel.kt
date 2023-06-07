package com.aplus.common.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplus.core.utils.DispatcherProvider
import com.aplus.core.utils.Resource
import com.aplus.domain.model.Genres
import com.aplus.domain.model.Movies
import com.aplus.domain.usecases.local.genres.GenresUseCases
import com.aplus.domain.usecases.local.movies.MoviesUseCases
import com.aplus.domain.usecases.remote.apimovie.ApiMovieUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MovieViewModel(
    private val apiMovieUseCases: ApiMovieUseCases,
    private val genresUseCases: GenresUseCases,
    private val moviesUseCases: MoviesUseCases,
    private val dispatcher: DispatcherProvider
): ViewModel() {
    protected val _movies: MutableStateFlow<Resource<List<Movies>>> =
        MutableStateFlow(Resource.loading(null))
    val movies = _movies.asStateFlow()
    private val _genres: MutableStateFlow<List<Genres>> = MutableStateFlow(listOf())
    val genres = _genres.asStateFlow()
    private val _favorit: MutableStateFlow<List<Movies>> = MutableStateFlow(listOf())
    val favorit = _favorit.asStateFlow()

    var page = 0
    var lastPositionAdapter = 0
    var listLoadedMovies: MutableList<Movies> = mutableListOf()


    protected fun getFavorite(){
        viewModelScope.launch {
            moviesUseCases.getListMovies().collectLatest {
                _favorit.emit(it)
            }
        }
    }

    protected fun getGenres(){
        viewModelScope.launch {
            genresUseCases.getListGenres().collectLatest { listGenres ->
                if (listGenres.isEmpty()) {
                    apiMovieUseCases.getGenresApi().let {
                        if (it.isSuccessful) {
                            if(it.body() != null) {
                                val genres = it.body()!!.genres
                                if(genres.isNotEmpty()) {
                                    withContext(dispatcher.io) {
                                        genresUseCases.insertListGenres(genres)
                                    }
                                }
                                _genres.emit(it.body()!!.genres)
                            }
                        }
                    }
                } else {
                    _genres.emit(listGenres)
                }
            }
        }
    }

    fun setLastPosition(position: Int) {
        lastPositionAdapter = position
    }

    fun insertDeleteFavorite(movies: Movies) {
        viewModelScope.launch(dispatcher.io) {
            if (_favorit.value.any { it.id == movies.id }) moviesUseCases.deleteSingleMovies(
                movies.id
            )
            else moviesUseCases.insertSingleMovies(movies)
        }
    }
}