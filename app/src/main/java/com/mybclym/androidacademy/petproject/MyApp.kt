package com.mybclym.androidacademy.petproject

import android.app.Application
import com.mybclym.androidacademy.petproject.DataModel.MoviesDataSource
import com.mybclym.androidacademy.petproject.DataModel.MoviesDataSourceImpl
import com.mybclym.androidacademy.petproject.ViewModels.MovieListViewModelFactory

interface DataProvider {
    fun dataSource(): MoviesDataSource
    fun viewModelFactory(): MovieListViewModelFactory
}

class MyApp : Application(), DataProvider {
    private lateinit var movieDataSource: MoviesDataSource
    private lateinit var viewModelFactory: MovieListViewModelFactory
    override fun onCreate() {
        super.onCreate()
        movieDataSource = MoviesDataSourceImpl(this)
        viewModelFactory = MovieListViewModelFactory(movieDataSource)
    }

    override fun dataSource(): MoviesDataSource = movieDataSource
    override fun viewModelFactory(): MovieListViewModelFactory = viewModelFactory
}