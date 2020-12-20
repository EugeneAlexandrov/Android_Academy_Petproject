package com.mybclym.androidacademy.petproject

import android.app.Application

interface DataProvider {
    fun dataSource(): MoviesDataSource
}

class MyApp : Application(), DataProvider {
    private lateinit var movieDataSource: MoviesDataSource
    override fun onCreate() {
        super.onCreate()
        movieDataSource = MoviesDataSourceImpl(this)
    }

    override fun dataSource(): MoviesDataSource = movieDataSource
}