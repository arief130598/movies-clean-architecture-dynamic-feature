package com.aplus.feature.detail.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.aplus.common.presentation.viewmodel.MovieViewModel
import com.aplus.core.utils.DispatcherProvider
import com.aplus.core.utils.NetworkHelper
import com.aplus.core.utils.Resource
import com.aplus.domain.model.Movies
import com.aplus.domain.model.Reviews
import com.aplus.domain.model.Videos
import com.aplus.domain.usecases.local.genres.GenresUseCases
import com.aplus.domain.usecases.local.movies.MoviesUseCases
import com.aplus.domain.usecases.remote.apimovie.ApiMovieUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val apiMovieUseCases: ApiMovieUseCases,
    genresUseCases: GenresUseCases,
    moviesUseCases: MoviesUseCases,
    private val dispatcher: DispatcherProvider,
    private val networkHelper: NetworkHelper
) : MovieViewModel(apiMovieUseCases, genresUseCases, moviesUseCases, dispatcher) {

    private val _videos: MutableStateFlow<Resource<List<Videos>>> =
        MutableStateFlow(Resource.loading(null))
    val videos = _videos.asStateFlow()
    private val _reviews: MutableStateFlow<Resource<List<Reviews>>> =
        MutableStateFlow(Resource.loading(null))
    val reviews = _reviews.asStateFlow()

    var pageReview = 0

    init {
        getFavorite()
        getGenres()
    }

    fun getSimilar(movieId: Int){
        page += 1
        viewModelScope.launch {
            _movies.emit(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiMovieUseCases.getSimilarApi(movieId, page).flowOn(dispatcher.io)
                    .catch { _movies.emit(Resource.error(it.toString(), null)) }
                    .collectLatest {
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
                    }
            } else _movies.emit(Resource.error("No internet connection", null))
        }
    }

    fun getVideos(movieId: Int){
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                apiMovieUseCases.getVideosApi(movieId).flowOn(dispatcher.io)
                    .catch { _movies.emit(Resource.error(it.toString(), null)) }
                    .collectLatest {
                        it.body()?.results?.let { data ->
                            var videosTrailer = data.filter { videos ->
                                videos.name.contains("Official Trailer")
                            }
                            if(videosTrailer.isEmpty()) {
                                videosTrailer = data.filter { videos ->
                                    videos.name.contains("Trailer")
                                }
                            }
                            _videos.emit(Resource.success(videosTrailer))
                        }
                    }
            } else _videos.emit(Resource.error("No internet connection", null))
        }
    }

    fun getReview(movieId: Int){
        pageReview += 1
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                apiMovieUseCases.getReviewsApi(movieId, pageReview).flowOn(dispatcher.io)
                    .catch { _movies.emit(Resource.error(it.toString(), null)) }
                    .collectLatest {
                        it.body()?.let { data ->
                            if(data.results.isNotEmpty()) {
                                _reviews.emit(Resource.success(it.body()!!.results))
                            }else{
                                _reviews.emit(Resource.error(it.errorBody().toString(), null))
                            }
                        }
                    }
            } else _reviews.emit(Resource.error("No internet connection", null))
        }
    }
}