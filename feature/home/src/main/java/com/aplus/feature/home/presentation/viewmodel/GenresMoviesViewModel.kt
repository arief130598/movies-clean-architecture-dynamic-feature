package com.aplus.feature.home.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.aplus.common.presentation.viewmodel.MovieViewModel
import com.aplus.core.utils.DispatcherProvider
import com.aplus.core.utils.NetworkHelper
import com.aplus.core.utils.Resource
import com.aplus.domain.usecases.local.genres.GenresUseCases
import com.aplus.domain.usecases.local.movies.MoviesUseCases
import com.aplus.domain.usecases.remote.apimovie.ApiMovieUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class GenresMoviesViewModel @Inject constructor(
    private val apiMovieUseCases: ApiMovieUseCases,
    genresUseCases: GenresUseCases,
    moviesUseCases: MoviesUseCases,
    private val dispatcher: DispatcherProvider,
    private val networkHelper: NetworkHelper
) : MovieViewModel(apiMovieUseCases, genresUseCases, moviesUseCases, dispatcher) {

    var currentGenres: String = ""

    init {
        getFavorite()
        getGenres()
    }

    fun getMovies(genres: String){
        page += 1
        viewModelScope.launch {
            _movies.emit(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiMovieUseCases.getMoviesApi(page, genres).flowOn(dispatcher.io)
                    .catch { _movies.emit(Resource.error(it.toString(), null)) }
                    .collectLatest {
                        if(it.body()?.results != null){
                            it.body()?.results?.let { data ->
                                _movies.emit(Resource.success(data))
                                listLoadedMovies.addAll(data)
                            }
                        } else {
                            _movies.emit(Resource.error(it.message(), null))
                        }
                    }
            } else _movies.emit(Resource.error("No internet connection", null))
        }
    }
}