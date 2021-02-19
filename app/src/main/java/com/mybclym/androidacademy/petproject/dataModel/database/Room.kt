package com.mybclym.androidacademy.petproject.dataModel.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Room
import com.mybclym.androidacademy.petproject.dataModel.*

@Dao
interface MoviesDao {
    @Transaction
    @Query("SELECT * FROM movies")
    fun getMovieWithGenresList(): LiveData<List<MovieWithGenres>>

    @Transaction
    @Query("SELECT * FROM movies WHERE movieId=:movieId")
    fun getMovie(movieId: Int): LiveData<MovieWithGenres>

    @Query("SELECT * FROM details WHERE detailsId=:movieId")
    fun getDetail(movieId: Int): LiveData<DetailsEntity>

    @Query("SELECT * FROM movies WHERE movieId=:movieId")
    fun getActors(movieId: Int): LiveData<MovieActors>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putMoviesEntity(movieList: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putGenresEntity(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putMovieGenreCrossRef(genres: List<MovieGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putDetailsEntity(details: DetailsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putActorsEntity(actors: List<ActorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putMovieActorCrossRef(movieActorCrossRef: List<MovieActorCrossRef>)

    @Transaction
    fun putMovieWithGenre(
        movieEntityList: List<MovieEntity>,
        movieGenreCrossRefs: List<MovieGenreCrossRef>
    ) {
        putMoviesEntity(movieEntityList)
        putMovieGenreCrossRef(movieGenreCrossRefs)
    }

    @Transaction
    fun putMovieWithActors(
        actors: List<ActorEntity>,
        movieActorCrossRef: List<MovieActorCrossRef>
    ) {
        putActorsEntity(actors)
        putMovieActorCrossRef(movieActorCrossRef)
    }
}

@Database(
    entities = [
        MovieEntity::class,
        GenreEntity::class,
        MovieGenreCrossRef::class,
        DetailsEntity::class,
        ActorEntity::class,
        MovieActorCrossRef::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
}

private lateinit var INSTANCE: MovieDatabase

fun getDatabase(context: Context): MovieDatabase {
    synchronized(MovieDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context,
                MovieDatabase::class.java,
                MoviesDbContract.DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}