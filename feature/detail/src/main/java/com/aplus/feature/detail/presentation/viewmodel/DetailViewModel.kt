package com.aplus.feature.detail.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplus.common.presentation.viewmodel.MovieViewModel
import com.aplus.core.utils.DispatcherProvider
import com.aplus.core.utils.NetworkHelper
import com.aplus.core.utils.Resource
import com.aplus.domain.model.Genres
import com.aplus.domain.model.Movies
import com.aplus.domain.model.MoviesVideos
import com.aplus.domain.usecases.local.genres.GenresUseCases
import com.aplus.domain.usecases.local.movies.MoviesUseCases
import com.aplus.domain.usecases.remote.apimovie.ApiMovieUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val apiMovieUseCases: ApiMovieUseCases,
    genresUseCases: GenresUseCases,
    moviesUseCases: MoviesUseCases,
    dispatcher: DispatcherProvider,
    private val networkHelper: NetworkHelper
) : MovieViewModel(apiMovieUseCases, genresUseCases, moviesUseCases, dispatcher) {

    private val _videos: MutableStateFlow<Resource<List<MoviesVideos>>> =
        MutableStateFlow(Resource.loading(null))
    val videos = _videos.asStateFlow()

    init {
        getFavorite()
        getGenres()
    }

    fun getSimilar(movieId: Int){
        page += 1
        viewModelScope.launch {
            _movies.emit(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiMovieUseCases.getSimilarApi(movieId, page).let {
                    if (it.isSuccessful) {
                        if(it.body() != null) {
                            val data = mutableListOf<Movies>()
                            it.body()?.let { value ->
                                for(i in value.results.indices){
                                    if(i < 5){
                                        data.add(value.results[i])
                                    }else{
                                        break
                                    }
                                }
                            }
                            _movies.emit(Resource.success(data))
                        }else{
                            _movies.emit(Resource.success(listOf()))
                        }
                    } else _movies.emit(Resource.error(it.errorBody().toString(), null))
                }
            } else _movies.emit(Resource.error("No internet connection", null))
        }
    }

    fun getVideos(movieId: Int){
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                apiMovieUseCases.getVideosApi(movieId).let {
                    if (it.isSuccessful) {
                        val data = it.body()?.results
                        if(data != null) {
                            val videosTrailer = data.filter { videos ->
                                videos.name.contains("Official Trailer")
                            }
                            _videos.emit(Resource.success(videosTrailer))
                        }else{
                            _videos.emit(Resource.success(listOf()))
                        }
                    } else _videos.emit(Resource.error(it.errorBody().toString(), null))
                }
            } else _videos.emit(Resource.error("No internet connection", null))
        }
    }
}