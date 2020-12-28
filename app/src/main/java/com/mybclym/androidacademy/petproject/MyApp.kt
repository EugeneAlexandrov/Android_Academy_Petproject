package com.mybclym.androidacademy.petproject

import android.app.Application
import com.mybclym.androidacademy.petproject.DataModel.MoviesDataSource
import com.mybclym.androidacademy.petproject.DataModel.MoviesDataSourceImpl
import com.mybclym.androidacademy.petproject.ViewModels.MoviesViewModelsFactory

interface DataProvider {
    fun dataSource(): MoviesDataSource
    fun moviesViewModelFactory(): MoviesViewModelsFactory
}

class MyApp : Application(), DataProvider {
    private lateinit var movieDataSource: MoviesDataSource
    private lateinit var moviesViewModelFactory: MoviesViewModelsFactory

    override fun onCreate() {
        super.onCreate()
        movieDataSource = MoviesDataSourceImpl(this)
        moviesViewModelFactory = MoviesViewModelsFactory(movieDataSource)
    }

    override fun dataSource(): MoviesDataSource = movieDataSource
    override fun moviesViewModelFactory(): MoviesViewModelsFactory = moviesViewModelFactory
}