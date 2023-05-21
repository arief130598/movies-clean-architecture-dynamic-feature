package com.aplus.feature.search.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplus.core.utils.NetworkHelper
import com.aplus.core.utils.Resource
import com.aplus.domain.model.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*

@HiltViewModel
class SearchViewModel @Inject constructor(
//    private val apiMovieDBRepo: ApiMovieDBRepoImpl,
                      private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<Movies>>>()
    val movies: LiveData<Resource<List<Movies>>>
        get() = _movies

    var page = 0
    var listLoadedMovies: MutableList<Movies> = mutableListOf()

    fun getMovies(query: String) {
        page += 1
        viewModelScope.launch {
            _movies.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
//                apiMovieDBRepo.getSearch(BuildConfig.API_KEY, query, page).let {
//                    if (it.isSuccessful) {
//                        if (it.body() != null) {
//                            _movies.postValue(Resource.success(it.body()!!.results))
//                            listLoadedMovies.addAll(it.body()!!.results)
//                        } else {
//                            _movies.postValue(Resource.success(listOf()))
//                        }
//                    } else _movies.postValue(Resource.error(it.errorBody().toString(), null))
//                }
            } else _movies.postValue(Resource.error("No internet connection", null))
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}