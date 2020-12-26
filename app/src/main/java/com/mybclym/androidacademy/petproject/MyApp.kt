package com.mybclym.androidacademy.petproject

import android.app.Application
import com.mybclym.androidacademy.petproject.DataModel.MoviesDataSource
import com.mybclym.androidacademy.petproject.DataModel.MoviesDataSourceImpl
import com.mybclym.androidacademy.petproject.ViewModels.MovieItemViewModelFactory
import com.mybclym.androidacademy.petproject.ViewModels.MovieListViewModelFactory

interface DataProvider {
    fun dataSource(): MoviesDataSource
    fun movieListViewModelFactory(): MovieListViewModelFactory
    fun movieItemViewModelFactory(): MovieItemViewModelFactory
}

class MyApp : Application(), DataProvider {
    private lateinit var movieDataSource: MoviesDataSource
    private lateinit var movieListViewModelFactory: MovieListViewModelFactory
    private lateinit var movieItemViewModelFactory: MovieItemViewModelFactory
    override fun onCreate() {
        super.onCreate()
        movieDataSource = MoviesDataSourceImpl(this)
        movieListViewModelFactory = MovieListViewModelFactory(movieDataSource)
        movieItemViewModelFactory = MovieItemViewModelFactory(movieDataSource)
    }

    override fun dataSource(): MoviesDataSource = movieDataSource
    override fun movieListViewModelFactory(): MovieListViewModelFactory = movieListViewModelFactory
    override fun movieItemViewModelFactory(): MovieItemViewModelFactory = movieItemViewModelFactory
}