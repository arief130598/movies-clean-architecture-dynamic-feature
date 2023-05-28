package com.aplus.feature.detail.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplus.common.presentation.viewmodel.MovieViewModel
import com.aplus.core.utils.NetworkHelper
import com.aplus.core.utils.Resource
import com.aplus.domain.model.Movies
import com.aplus.domain.usecases.local.genres.GenresUseCases
import com.aplus.domain.usecases.local.movies.MoviesUseCases
import com.aplus.domain.usecases.remote.apimovie.ApiMovieUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val apiMovieUseCases: ApiMovieUseCases,
    genresUseCases: GenresUseCases,
    moviesUseCases: MoviesUseCases,
    private val networkHelper: NetworkHelper
) : MovieViewModel(apiMovieUseCases, genresUseCases, moviesUseCases) {

    init {
        getFavorite()
        getGenres()
    }

    fun getSimilar(movieId: Int){
        page += 1
        viewModelScope.launch {
            _movies.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiMovieUseCases.getSimilarApi(movieId, page).let {
                    if (it.isSuccessful) {
                        if(it.body() != null) {
                            val data = mutableListOf<Movies>()
                            for(i in it.body()!!.results.indices){
                                if(i < 5){
                                    data.add(it.body()!!.results[i])
                                }else{
                                    break
                                }
                            }
                            _movies.postValue(Resource.success(data))
                        }else{
                            _movies.postValue(Resource.success(listOf()))
                        }
                    } else _movies.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _movies.postValue(Resource.error("No internet connection", null))
        }
    }
}