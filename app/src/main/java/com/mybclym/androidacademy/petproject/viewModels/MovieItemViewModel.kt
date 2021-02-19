package com.mybclym.androidacademy.petproject.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.mybclym.androidacademy.petproject.dataModel.domainModel.Movie
import com.mybclym.androidacademy.petproject.dataModel.MovieRepository
import com.mybclym.androidacademy.petproject.dataModel.MovieRepositoryImp
import com.mybclym.androidacademy.petproject.dataModel.domainModel.Actor
import kotlinx.coroutines.launch
import java.io.IOException

class MovieItemViewModel(private val repository: MovieRepositoryImp, private val id: Int) :
    ViewModel() {

    var movieItem = repository.movieItem

    private var _movieActors = MutableLiveData<List<Actor>>()
    val movieActors: LiveData<List<Actor>>
        get() = _movieActors

    private var _details = MutableLiveData<Int>()
    val details: LiveData<Int>
        get() = _details

    private var _eventActorsNetworkError = MutableLiveData<Boolean>(false)
    val eventActorsNetworkError: LiveData<Boolean>
        get() = _eventActorsNetworkError

    private var _eventMovieNetworkError = MutableLiveData<Boolean>(false)
    val eventMovieNetworkError: LiveData<Boolean>
        get() = _eventMovieNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private var _mutableMovieLoadingState = MutableLiveData<Boolean>(false)
    val movieLoading: LiveData<Boolean> get() = _mutableMovieLoadingState

    private var _mutableActorsLoadingState = MutableLiveData<Boolean>(false)
    val actorsLoading: LiveData<Boolean> get() = _mutableActorsLoadingState

    init {
        refreshMovieItemFromRepository(id)
    }

    fun refreshMovieItemFromRepository(id: Int) {
        viewModelScope.launch {
            try {
                _mutableMovieLoadingState.value = true
                movieItem = repository.movieItem
                _details = repository.details as MutableLiveData<Int>
                _movieActors = repository.actorList as MutableLiveData<List<Actor>>
                repository.refreshMovieItem(id)
                _mutableMovieLoadingState.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                Log.d("TEST", "${networkError.message}")
                _eventActorsNetworkError.value = true
                _mutableMovieLoadingState.value = false
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
}