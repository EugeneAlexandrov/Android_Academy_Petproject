package com.mybclym.androidacademy.petproject.dataModel

import android.content.Context
import androidx.lifecycle.LiveData
import com.mybclym.androidacademy.petproject.Response.*
import com.mybclym.androidacademy.petproject.dataModel.database.getDatabase

interface LocalDataSource {
    fun  getNowPlayingMovies(): LiveData<List<MovieWithGenres>>
    fun getMovie(id: Int): LiveData<MovieWithGenres>
    suspend fun getMovieDetails(movieId: Int): LiveData<DetailsEntity>
    suspend fun getActors(movieId: Int): LiveData<MovieActors>
    fun setNowPlayingMovies(
        movieList: List<JsonNowPlayingResults>,
        configurationImages: JsonConfigurationImages
    )

    fun setMovieDetails(details: DetailsResponse)
    fun setGenres(genres: GenresResponse)
    fun setActors(actors: CreditsResponse, configurationImages: JsonConfigurationImages)
}

class LocalDataSourceImpl(appContext: Context) : LocalDataSource {

    private val database = getDatabase(appContext)

    override fun getNowPlayingMovies(): LiveData<List<MovieWithGenres>> =
        database.moviesDao.getMovieWithGenresList()

    override fun getMovie(id: Int): LiveData<MovieWithGenres> =
        database.moviesDao.getMovie(id)

    override suspend fun getMovieDetails(movieId: Int): LiveData<DetailsEntity> =
        database.moviesDao.getDetail(movieId)

    override suspend fun getActors(movieId: Int): LiveData<MovieActors> =
        database.moviesDao.getActors(movieId)

    override fun setNowPlayingMovies(
        movieList: List<JsonNowPlayingResults>,
        configurationImages: JsonConfigurationImages
    ) {
        database.moviesDao.putMovieWithGenre(
            movieList.asMovieEntity(
                configurationImages.imageUrl,
                configurationImages.backdropSize,
                configurationImages.posterSize
            ),
            movieList.asMovieGenreCrossRef()
        )
    }

    override fun setMovieDetails(
        details: DetailsResponse
    ) {
        database.moviesDao.putDetailsEntity(
            details.asDetailsEntity()
        )
    }

    override fun setGenres(genres: GenresResponse) {
        database.moviesDao.putGenresEntity(genres.asGenresEntity())
    }

    override fun setActors(actors: CreditsResponse, configurationImages: JsonConfigurationImages) {
        database.moviesDao.putMovieWithActors(
            actors.asActorsEntity(
                configurationImages.imageUrl,
                configurationImages.profileSize
            ),
            actors.asMovieActorCrossRef()
        )
    }
}


