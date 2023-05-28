package com.aplus.common.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplus.core.utils.Resource
import com.aplus.domain.model.Genres
import com.aplus.domain.model.Movies
import com.aplus.domain.usecases.local.genres.GenresUseCases
import com.aplus.domain.usecases.local.movies.MoviesUseCases
import com.aplus.domain.usecases.remote.apimovie.ApiMovieUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MovieViewModel(
    private val apiMovieUseCases: ApiMovieUseCases,
    private val genresUseCases: GenresUseCases,
    private val moviesUseCases: MoviesUseCases
): ViewModel() {
    protected val _movies = MutableLiveData<Resource<List<Movies>>>()
    val movies: LiveData<Resource<List<Movies>>> = _movies
    private val _genres = MutableLiveData<List<Genres>>()
    val genres: LiveData<List<Genres>> = _genres
    private val _favorit = MutableLiveData<List<Movies>>()
    val favorit: LiveData<List<Movies>> = _favorit

    var page = 0
    var lastPositionAdapter = 0
    var listLoadedMovies: MutableList<Movies> = mutableListOf()


    protected fun getFavorite(){
        viewModelScope.launch {
            moviesUseCases.getListMovies().collectLatest {
                _favorit.postValue(it)
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
                                    withContext(Dispatchers.IO) {
                                        genresUseCases.insertListGenres(genres)
                                    }
                                }
                                _genres.postValue(it.body()!!.genres)
                            }
                        }
                    }
                } else {
                    _genres.postValue(listGenres)
                }
            }
        }
    }

    fun setLastPosition(position: Int) {
        lastPositionAdapter = position
    }

    fun insertDeleteFavorite(movies: Movies) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_favorit.value!!.any { it.id == movies.id }) moviesUseCases.deleteSingleMovies(
                movies.id
            )
            else moviesUseCases.insertSingleMovies(movies)
        }
    }
}