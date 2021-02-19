package com.mybclym.androidacademy.petproject.dataModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mybclym.androidacademy.petproject.Response.JsonConfigurationImages
import com.mybclym.androidacademy.petproject.dataModel.domainModel.Actor
import com.mybclym.androidacademy.petproject.dataModel.domainModel.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.concurrent.TimeUnit

interface MovieRepository {
    suspend fun refreshMoviesList()
    suspend fun refreshMovieItem(id: Int)
}

class MovieRepositoryImp(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) : MovieRepository {
    var movieList: LiveData<List<Movie>> =
        Transformations.map(localDataSource.getNowPlayingMovies()) {
            it.asDomainModel()
        }
    lateinit var config: JsonConfigurationImages
    var movieItem: LiveData<Movie> = MutableLiveData(Movie())
    var actorList: LiveData<List<Actor>> = MutableLiveData(emptyList())
    var details: LiveData<Int> = MutableLiveData(0)

    override suspend fun refreshMoviesList() {
        withContext(Dispatchers.IO) {
            config = remoteDataSource.loadConfig()
            val genres = remoteDataSource.loadGenres()
            localDataSource.setGenres(genres)
            val movieList = remoteDataSource.loadNowPlayingResults()
            localDataSource.setNowPlayingMovies(movieList, config)
        }
    }

    override suspend fun refreshMovieItem(id: Int) {
        withContext(Dispatchers.IO) {
            movieItem = Transformations.map(localDataSource.getMovie(id)) {
                it.asDomainModel()
            }
            actorList = Transformations.map(localDataSource.getActors(id)) {
                it.asDomainModel()
            }
            details = Transformations.map(localDataSource.getMovieDetails(id)) {
                it.runtime
            }
            val actors = remoteDataSource.loadActors(id)
            val details = remoteDataSource.loadMovieById(id)
            localDataSource.setMovieDetails(details)
            localDataSource.setActors(actors, config)
            var actorstest = localDataSource.getActors(id)
            Log.d("Test","")
        }
    }

    companion object {
        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }

}
