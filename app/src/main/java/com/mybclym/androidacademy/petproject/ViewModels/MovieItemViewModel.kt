package com.mybclym.androidacademy.petproject.ViewModels

import androidx.lifecycle.*
import com.mybclym.androidacademy.petproject.DataModel.Movie
import com.mybclym.androidacademy.petproject.DataModel.MoviesDataSource
import kotlinx.coroutines.launch

class MovieItemViewModel(private val dataSource: MoviesDataSource) : ViewModel() {

    private val movieItemLiveData = MutableLiveData(Movie())
    val movieItem: LiveData<Movie> get() = movieItemLiveData

    fun loadMovieItem(id: Int) {
        viewModelScope.launch {
            movieItemLiveData.value = dataSource.getMovieByIdAsync(id)
        }
    }

}