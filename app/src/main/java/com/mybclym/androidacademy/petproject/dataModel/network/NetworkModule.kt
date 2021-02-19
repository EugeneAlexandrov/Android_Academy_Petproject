package com.mybclym.androidacademy.petproject

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mybclym.androidacademy.petproject.Response.*
import com.mybclym.androidacademy.petproject.services.MovieApiService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

private const val API_KEY = "09212fc7232b908173611c963837e669"

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

object NetworkModule {

    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(Interceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        })
        .addNetworkInterceptor(loggingInterceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        coerceInputValues = true

    }

    private val contentType = "application/json".toMediaType()

    @ExperimentalSerializationApi
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(httpClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    val movieApiService: MovieApiService = retrofit.create()
}