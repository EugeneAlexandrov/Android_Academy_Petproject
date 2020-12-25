package com.mybclym.androidacademy.petproject.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mybclym.androidacademy.petproject.DataModel.MoviesDataSource

class MovieListViewModelFactory(private val arg: MoviesDataSource) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MovieListViewModel(arg) as T
}