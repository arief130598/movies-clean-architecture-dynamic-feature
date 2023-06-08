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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
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

    fun getGenres(){
        viewModelScope.launch {
            _genres.emit(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiMovieUseCases.getGenresApi().flowOn(dispatcher.io)
                    .catch { _genres.emit(Resource.error(it.toString(), null)) }
                    .collectLatest {
                        if(it.body()?.genres != null){
                            it.body()?.genres?.let { data ->
                                if(data.isNotEmpty()) {
                                    withContext(dispatcher.io) {
                                        genresUseCases.insertListGenres(data)
                                    }
                                }
                                _genres.emit(Resource.success(data))
                            }
                        }else{
                            _genres.emit(Resource.error(it.message(), null))
                        }
                    }
            } else _genres.emit(Resource.error("No internet connection", null))
        }
    }
}