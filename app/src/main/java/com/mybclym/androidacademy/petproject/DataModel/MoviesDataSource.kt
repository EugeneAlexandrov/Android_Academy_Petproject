package com.mybclym.androidacademy.petproject.DataModel

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MoviesDataSource {
    suspend fun getMoviesAsync(): List<Movie>
    suspend fun getMovieByIdAsync(movieId: Int): Movie?
}

class MoviesDataSourceImpl(private val context: Context) : MoviesDataSource {

    override suspend fun getMoviesAsync(): List<Movie> =
        withContext(Dispatchers.IO) {
            loadMovies(context)
        }

    override suspend fun getMovieByIdAsync(movieId: Int): Movie? =
        withContext(Dispatchers.IO) {
            loadMovies(context).find { movieId == it.id }
        }
}