package com.mybclym.androidacademy.petproject

import android.app.Application
import com.mybclym.androidacademy.petproject.dataModel.*
import com.mybclym.androidacademy.petproject.viewModels.MoviesViewModelsFactory

interface DataProvider {
    fun moviesViewModelFactory(): MoviesViewModelsFactory
    fun movieRepository(): MovieRepositoryImp
}

class MyApp : Application(), DataProvider {
    private lateinit var localDataSource: LocalDataSource
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var movieRepository: MovieRepositoryImp
    private lateinit var moviesViewModelFactory: MoviesViewModelsFactory

    override fun onCreate() {
        super.onCreate()
        localDataSource = LocalDataSourceImpl(applicationContext)
        remoteDataSource = RemoteDataSourceImpl(NetworkModule)
        movieRepository = MovieRepositoryImp(localDataSource, remoteDataSource)
        moviesViewModelFactory = MoviesViewModelsFactory(movieRepository)
    }

    override fun moviesViewModelFactory(): MoviesViewModelsFactory = moviesViewModelFactory
    override fun movieRepository(): MovieRepositoryImp = movieRepository
}