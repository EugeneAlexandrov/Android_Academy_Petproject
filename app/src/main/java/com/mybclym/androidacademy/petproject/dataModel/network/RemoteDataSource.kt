package com.mybclym.androidacademy.petproject.dataModel

import com.mybclym.androidacademy.petproject.NetworkModule
import com.mybclym.androidacademy.petproject.Response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RemoteDataSource {
    suspend fun loadNowPlayingResults(): List<JsonNowPlayingResults>
    suspend fun loadMovieById(movieId: Int): DetailsResponse
    suspend fun loadConfig(): JsonConfigurationImages
    suspend fun loadGenres(): GenresResponse
    suspend fun loadActors(movieId: Int): CreditsResponse
}

class RemoteDataSourceImpl(
    private val networkModule: NetworkModule
) : RemoteDataSource {

    override suspend fun loadNowPlayingResults(): List<JsonNowPlayingResults> =
        networkModule.movieApiService.getNowPlaying().results

    override suspend fun loadMovieById(movieId: Int): DetailsResponse =
        networkModule.movieApiService.getMovieDetails(movieId)

    override suspend fun loadConfig(): JsonConfigurationImages =
        networkModule.movieApiService.getConfiguration().images

    override suspend fun loadGenres(): GenresResponse =
        networkModule.movieApiService.getGenres()

    override suspend fun loadActors(movieId: Int): CreditsResponse =
        networkModule.movieApiService.getMovieActors(movieId)
}