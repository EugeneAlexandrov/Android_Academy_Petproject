package com.mybclym.androidacademy.petproject.dataModel

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.mybclym.androidacademy.petproject.dataModel.database.MoviesDbContract
import com.mybclym.androidacademy.petproject.dataModel.domainModel.Actor
import com.mybclym.androidacademy.petproject.dataModel.domainModel.Genre
import com.mybclym.androidacademy.petproject.dataModel.domainModel.Movie

@Entity(
    tableName = MoviesDbContract.Movie.TABLE_NAME,
    indices = [Index(MoviesDbContract.Movie.COLUMN_NAME_ID)]
)
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = MoviesDbContract.Movie.COLUMN_NAME_ID)
    val movieId: Int,

    @ColumnInfo(name = MoviesDbContract.Movie.COLUMN_NAME_TITLE)
    val title: String = "",

    @ColumnInfo(name = MoviesDbContract.Movie.COLUMN_NAME_OVERVIEW)
    val overview: String = "",

    @ColumnInfo(name = MoviesDbContract.Movie.COLUMN_NAME_POSTER)
    val poster: String = "",

    @ColumnInfo(name = MoviesDbContract.Movie.COLUMN_NAME_BACKDROP)
    val backdrop: String = "",

    @ColumnInfo(name = MoviesDbContract.Movie.COLUMN_NAME_RATING)
    val ratings: Float = 0f,

    @ColumnInfo(name = MoviesDbContract.Movie.COLUMN_NAME_NUMBER_OF_RATING)
    val numberOfRatings: Int = 0,

    @ColumnInfo(name = MoviesDbContract.Movie.COLUMN_NAME_RELEASE_DATE)
    val release: String = "",

    @ColumnInfo(name = MoviesDbContract.Movie.COLUMN_NAME_ADULT)
    val adult: Boolean = true
)

@Entity(
    tableName = MoviesDbContract.Genre.TABLE_NAME,
    indices = [androidx.room.Index(MoviesDbContract.Genre.COLUMN_NAME_GENRE_ID)]
)
data class GenreEntity(
    @PrimaryKey
    @ColumnInfo(name = MoviesDbContract.Genre.COLUMN_NAME_GENRE_ID)
    val genreId: Int,

    @ColumnInfo(name = MoviesDbContract.Genre.COLUMN_NAME_NAME)
    val name: String
)

@Entity(
    tableName = MoviesDbContract.Details.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = arrayOf(MoviesDbContract.Movie.COLUMN_NAME_ID),
        childColumns = arrayOf(MoviesDbContract.Details.COLUMN_NAME_DETAILS_ID),
        onDelete = CASCADE
    )],
    indices = [androidx.room.Index(MoviesDbContract.Details.COLUMN_NAME_DETAILS_ID)]
)
data class DetailsEntity(
    @PrimaryKey
    @ColumnInfo(name = MoviesDbContract.Details.COLUMN_NAME_DETAILS_ID)
    val detailsId: Int,

    @ColumnInfo(name = MoviesDbContract.Details.COLUMN_NAME_RUNTIME)
    val runtime: Int,
)

@Entity(
    tableName = MoviesDbContract.Actor.TABLE_NAME,
    indices = [androidx.room.Index(MoviesDbContract.Actor.COLUMN_NAME_ID)],
    primaryKeys = [
        MoviesDbContract.Actor.COLUMN_NAME_CREDIT_ID]
)
data class ActorEntity(
    @ColumnInfo(name = MoviesDbContract.Actor.COLUMN_NAME_ID)
    val actorId: Int,

    @ColumnInfo(name = MoviesDbContract.Actor.COLUMN_NAME_CREDIT_ID)
    val creditId: String,

    @ColumnInfo(name = MoviesDbContract.Actor.COLUMN_NAME_NAME)
    val actorName: String,

    @ColumnInfo(name = MoviesDbContract.Actor.COLUMN_NAME_PICTURE)
    val picture: String,

    @ColumnInfo(name = MoviesDbContract.Actor.COLUMN_NAME_CHARACTER)
    val character: String
)

@Entity(
    primaryKeys = [
        MoviesDbContract.Movie.COLUMN_NAME_ID,
        MoviesDbContract.Genre.COLUMN_NAME_GENRE_ID],
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = arrayOf(MoviesDbContract.Movie.COLUMN_NAME_ID),
        childColumns = arrayOf(MoviesDbContract.Movie.COLUMN_NAME_ID),
        onDelete = CASCADE
    ),
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = arrayOf(MoviesDbContract.Genre.COLUMN_NAME_GENRE_ID),
            childColumns = arrayOf(MoviesDbContract.Genre.COLUMN_NAME_GENRE_ID),
            onDelete = CASCADE
        )]
)
data class MovieGenreCrossRef(
    val movieId: Int,
    val genreId: Int
)

@Entity(
    primaryKeys = [
        MoviesDbContract.Movie.COLUMN_NAME_ID,
        MoviesDbContract.Actor.COLUMN_NAME_CREDIT_ID],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = arrayOf(MoviesDbContract.Movie.COLUMN_NAME_ID),
            childColumns = arrayOf(MoviesDbContract.Movie.COLUMN_NAME_ID),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = ActorEntity::class,
            parentColumns = arrayOf(MoviesDbContract.Actor.COLUMN_NAME_CREDIT_ID),
            childColumns = arrayOf(MoviesDbContract.Actor.COLUMN_NAME_CREDIT_ID),
            onDelete = CASCADE
        )]
)
data class MovieActorCrossRef(
    val movieId: Int,
    val creditId: String
)

// отправить его в recycler
data class MovieWithGenres(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = MoviesDbContract.Movie.COLUMN_NAME_ID,
        entityColumn = MoviesDbContract.Genre.COLUMN_NAME_GENRE_ID,
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<GenreEntity>
)

data class MovieActors(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = MoviesDbContract.Movie.COLUMN_NAME_ID,
        entityColumn = MoviesDbContract.Actor.COLUMN_NAME_CREDIT_ID,
        associateBy = Junction(MovieActorCrossRef::class)
    )
    val actors: List<ActorEntity>
)

fun List<MovieWithGenres>.asDomainModel(): List<Movie> {
    return map {
        Movie(
            id = it.movie.movieId,
            title = it.movie.title,
            overview = it.movie.overview,
            poster = it.movie.poster,
            backdrop = it.movie.backdrop,
            release = it.movie.release,
            ratings = it.movie.ratings,
            numberOfRatings = it.movie.numberOfRatings,
            minimumAge = if (it.movie.adult) 16 else 13,
            genres = it.genres.map { genreEntity ->
                Genre(
                    id = genreEntity.genreId,
                    name = genreEntity.name
                )
            }
        )
    }
}

fun MovieWithGenres.asDomainModel(): Movie {
    return Movie(
        id = this.movie.movieId,
        title = this.movie.title,
        overview = this.movie.overview,
        poster = this.movie.poster,
        backdrop = this.movie.backdrop,
        release = this.movie.release,
        ratings = this.movie.ratings,
        numberOfRatings = this.movie.numberOfRatings,
        minimumAge = if (this.movie.adult) 16 else 13,
        genres = this.genres.map { genreEntity ->
            Genre(
                id = genreEntity.genreId,
                name = genreEntity.name
            )
        }
    )
}

fun MovieActors.asDomainModel(): List<Actor> {
    return this.actors.map {
        Actor(
            id = it.actorId,
            name = it.actorName,
            picture = it.picture,
            character = it.character,
            creditId = it.creditId
        )
    }
}
