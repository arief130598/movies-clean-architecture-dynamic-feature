package com.aplus.feature.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.MoviesResponse
import com.aplus.domain.usecases.remote.listmovies.ListMoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val listMoviesUseCases: ListMoviesUseCases
) : ViewModel() {

    private val _movies2: MutableStateFlow<ResponseResult<MoviesResponse>> =
        MutableStateFlow(ResponseResult.Loading)
    val movies2 = _movies2.asStateFlow()

    fun getMovies(){
        viewModelScope.launch {
            listMoviesUseCases.getPopularMovies(1).flowOn(Dispatchers.IO)
                .catch { _movies2.emit(ResponseResult.Error(Throwable(it.message))) }
                .collectLatest {
                    _movies2.emit(it)
                }
        }
    }
}