package com.mybclym.androidacademy.petproject.ViewModels

import androidx.lifecycle.*
import com.mybclym.androidacademy.petproject.DataModel.Movie
import com.mybclym.androidacademy.petproject.DataModel.MoviesDataSource
import kotlinx.coroutines.launch

class MovieListViewModel(private val dataSource: MoviesDataSource) : ViewModel() {

    private val movieListLiveData = MutableLiveData<List<Movie>>(emptyList())
    val movieList: LiveData<List<Movie>> get() = movieListLiveData

    fun loadMoviesList() {
        viewModelScope.launch {
            movieListLiveData.value = dataSource.getMoviesAsync()
        }
    }

}