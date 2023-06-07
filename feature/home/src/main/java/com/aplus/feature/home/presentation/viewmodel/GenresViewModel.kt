package com.aplus.feature.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplus.core.utils.DispatcherProvider
import com.aplus.core.utils.NetworkHelper
import com.aplus.core.utils.Resource
import com.aplus.domain.model.Genres
import com.aplus.domain.usecases.local.genres.GenresUseCases
import com.aplus.domain.usecases.remote.apimovie.ApiMovieUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val apiMovieUseCases: ApiMovieUseCases,
    private val genresUseCases: GenresUseCases,
    private val networkHelper: NetworkHelper,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _genres: MutableStateFlow<Resource<List<Genres>>> =
        MutableStateFlow(Resource.loading(null))
    val genres = _genres.asStateFlow()

    init {
        getGenres()
    }

    private fun getGenres(){
        viewModelScope.launch {
            _genres.emit(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiMovieUseCases.getGenresApi().let {
                    if (it.isSuccessful) {
                        if(it.body() != null) {
                            val genres = it.body()!!.genres
                            if(genres.isNotEmpty()) {
                                withContext(dispatcher.io) {
                                    genresUseCases.insertListGenres(genres)
                                }
                            }
                            _genres.emit(Resource.success(genres))
                        }else{
                            _genres.emit(Resource.success(listOf()))
                        }
                    } else _genres.emit(Resource.error(it.errorBody().toString(), null))
                }
            } else _genres.emit(Resource.error("No internet connection", null))
        }
    }
}