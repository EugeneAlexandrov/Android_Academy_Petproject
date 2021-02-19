package com.mybclym.androidacademy.petproject.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mybclym.androidacademy.petproject.dataModel.MovieRepository
import com.mybclym.androidacademy.petproject.dataModel.MovieRepositoryImp

class MoviesViewModelsFactory(private val arg: MovieRepositoryImp) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieListViewModel::class.java -> MovieListViewModel(arg)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}

class MoviesItemViewModelsFactory(private val arg: MovieRepositoryImp, private val id:Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieItemViewModel::class.java -> MovieItemViewModel(arg,id)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}