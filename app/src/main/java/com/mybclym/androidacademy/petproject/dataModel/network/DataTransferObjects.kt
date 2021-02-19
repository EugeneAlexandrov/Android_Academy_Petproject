package com.mybclym.androidacademy.petproject.Response

import com.mybclym.androidacademy.petproject.dataModel.*
import com.mybclym.androidacademy.petproject.dataModel.domainModel.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonGenre(
    val id: Int,
    @SerialName("name")
    val name: String
)

@Serializable
class CreditsResponse(
    val id: Int,
    @SerialName("cast")
    val cast: List<JsonActor>
)

@Serializable
data class JsonActor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String?,
    @SerialName("credit_id")
    val creditId: String,
    @SerialName("character")
    val character: String
)

@Serializable
class NowPlayingResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<JsonNowPlayingResults>
)

@Serializable
class JsonNowPlayingResults(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String?,
    @SerialName("backdrop_path")
    val backdropPicture: String?,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
    @SerialName("overview")
    val overview: String,
    @SerialName("adult")
    val adult: Boolean
)

@Serializable
class DetailsResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("runtime")
    val runtime: Int?
)

@Serializable
data class JsonConfigurationImages(
    @SerialName("secure_base_url")
    val imageUrl: String,
    @SerialName("backdrop_sizes")
    val backdropSize: List<String>,
    @SerialName("poster_sizes")
    val posterSize: List<String>,
    @SerialName("profile_sizes")
    val profileSize: List<String>
)

@Serializable
data class ConfigurationResponse(
    @SerialName("images")
    val images: JsonConfigurationImages
)

@Serializable
data class GenresResponse(
    @SerialName("genres")
    val genres: List<JsonGenre>
)

fun List<JsonNowPlayingResults>.asMovieEntity(
    imageUrl: String,
    backdropSize: List<String>,
    posterSize: List<String>
): List<MovieEntity> {
    return map { jsonMovie ->
        MovieEntity(
            movieId = jsonMovie.id,
            overview = jsonMovie.overview,
            title = jsonMovie.title,
            poster = imageUrl + posterSize[3] + jsonMovie.posterPicture,
            backdrop = imageUrl + backdropSize[1] + jsonMovie.backdropPicture,
            release = jsonMovie.releaseDate,
            ratings = jsonMovie.ratings,
            numberOfRatings = jsonMovie.votesCount,
            adult = jsonMovie.adult
        )
    }
}

fun List<JsonNowPlayingResults>.asMovieGenreCrossRef(): List<MovieGenreCrossRef> {
    return flatMap { jsonMovie ->
        jsonMovie.genreIds.map {
            MovieGenreCrossRef(jsonMovie.id, it)
        }
    }
}

fun DetailsResponse.asDetailsEntity(): DetailsEntity {
    return DetailsEntity(
        detailsId = this.id,
        runtime = this.runtime ?: 0
    )
}

fun GenresResponse.asGenresEntity(): List<GenreEntity> {
    return genres.map {
        GenreEntity(
            genreId = it.id,
            name = it.name
        )
    }
}

fun CreditsResponse.asActorsEntity(
    imageUrl: String,
    profileSize: List<String>,
): List<ActorEntity> {
    return cast.map { actor ->
        ActorEntity(
            actorId = actor.id,
            creditId = actor.creditId,
            actorName = actor.name,
            picture = imageUrl + profileSize[1] + actor.profilePicture,
            character = actor.character
        )
    }
}

fun CreditsResponse.asMovieActorCrossRef():
        List<MovieActorCrossRef> {
    return cast.map {
        MovieActorCrossRef(
            movieId = this.id,
            creditId = it.creditId
        )
    }
}