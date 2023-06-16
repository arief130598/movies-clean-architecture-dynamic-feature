package com.aplus.feature.home.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.aplus.common.presentation.viewmodel.MovieViewModel
import com.aplus.core.utils.DispatcherProvider
import com.aplus.core.utils.NetworkHelper
import com.aplus.core.utils.Resource
import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.usecases.local.genres.GenresUseCases
import com.aplus.domain.usecases.local.movies.MoviesUseCases
import com.aplus.domain.usecases.remote.apimovie.ApiMovieUseCases
import com.aplus.domain.usecases.remote.listmovies.ListMoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


@HiltViewModel
class PopularViewModel @Inject constructor(
    private val apiMovieUseCases: ApiMovieUseCases,
    private val listMoviesUseCases: ListMoviesUseCases,
    genresUseCases: GenresUseCases,
    moviesUseCases: MoviesUseCases,
    private val dispatcher: DispatcherProvider,
    private val networkHelper: NetworkHelper
) : MovieViewModel(apiMovieUseCases, genresUseCases, moviesUseCases, dispatcher) {

    private val _movies2: MutableStateFlow<ResponseResult<MoviesResponse>> =
        MutableStateFlow(ResponseResult.Loading)
    val movies2 = _movies2.asStateFlow()

    init {
        getFavorite()
        getGenres()
    }

    fun getMovies(){
        page += 1
        viewModelScope.launch {
            listMoviesUseCases.getPopularMovies(page).flowOn(dispatcher.io)
                .catch { _movies.emit(Resource.error(it.toString(), null)) }
                .collectLatest { _movies2.emit(it) }
        }
    }
}