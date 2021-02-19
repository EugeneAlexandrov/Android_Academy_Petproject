package com.mybclym.androidacademy.petproject.viewModels

import androidx.lifecycle.*
import com.mybclym.androidacademy.petproject.dataModel.domainModel.Movie
import com.mybclym.androidacademy.petproject.dataModel.MovieRepository
import com.mybclym.androidacademy.petproject.dataModel.MovieRepositoryImp
import kotlinx.coroutines.launch
import java.io.IOException

class MovieListViewModel(private val repository: MovieRepositoryImp) : ViewModel() {

    val movieList = repository.movieList

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _mutableLoadingState = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _mutableLoadingState

    init {
        refreshMovieListFromRepository()
    }

    private fun refreshMovieListFromRepository() = viewModelScope.launch {
        try {
            _mutableLoadingState.value = true
            repository.refreshMoviesList()
            _mutableLoadingState.value = false
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        } catch (networkError: IOException) {
            // Show a Toast error message and hide the progress bar.
            _eventNetworkError.value = true
            _mutableLoadingState.value = false
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
}