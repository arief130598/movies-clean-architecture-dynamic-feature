package com.aplus.feature.favorite.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplus.domain.model.Genres
import com.aplus.domain.model.Movies
import com.aplus.domain.usecases.local.genres.GenresUseCases
import com.aplus.domain.usecases.local.movies.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val genresUseCases: GenresUseCases,
    private val moviesUseCases: MoviesUseCases
): ViewModel() {
    private val _movies: MutableStateFlow<HashMap<Int, Movies>> = MutableStateFlow(hashMapOf())
    val movies = _movies.asStateFlow()
    private val _genres: MutableStateFlow<List<Genres>> = MutableStateFlow(listOf())
    val genres = _genres.asStateFlow()
    private val _favorit: MutableStateFlow<List<Movies>> = MutableStateFlow(listOf())
    val favorit = _favorit.asStateFlow()

    init {
        getGenres()
    }

    private fun getGenres(){
        viewModelScope.launch {
            genresUseCases.getListGenres().collectLatest { listGenres ->
                _genres.emit(listGenres)
            }
        }
    }

    fun getFavorite(){
        viewModelScope.launch {
            moviesUseCases.getListMovies().collectLatest {
                _favorit.emit(it)
                cancel()
            }
        }
    }

    fun insertDeleteFavorite(movies: Movies, position: Int) {
        val data = HashMap<Int, Movies>()
        data[position] = movies
        viewModelScope.launch(Dispatchers.IO) {
            if(_favorit.value.contains(movies)) moviesUseCases.deleteSingleMovies(movies.id)
            else moviesUseCases.insertSingleMovies(movies)
            _movies.emit(data)
        }
    }
}