package com.mybclym.androidacademy.petproject.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mybclym.androidacademy.petproject.DataModel.MoviesDataSource

class MoviesViewModelsFactory(private val arg: MoviesDataSource) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieListViewModel::class.java -> MovieListViewModel(arg)
        MovieItemViewModel::class.java -> MovieItemViewModel(arg)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}