package com.mybclym.androidacademy.petproject.services

import com.mybclym.androidacademy.petproject.Response.*
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
    ): NowPlayingResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): DetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieActors(
        @Path("movie_id") movieId: Int
    ): CreditsResponse

    @GET("configuration")
    suspend fun getConfiguration(
    ): ConfigurationResponse

    @GET("genre/movie/list")
    suspend fun getGenres(
    ): GenresResponse
}