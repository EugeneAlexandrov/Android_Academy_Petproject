package com.mybclym.androidacademy.petproject.Services

import com.mybclym.androidacademy.petproject.Response.*
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
    ): Results

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): JsonMovie

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieActors(
        @Path("movie_id") movieId: Int
    ): JsonMovieCredits

    @GET("configuration")
    suspend fun getConfiguration(
    ): Configuration
}