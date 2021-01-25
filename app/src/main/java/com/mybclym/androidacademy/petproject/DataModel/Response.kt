package com.mybclym.androidacademy.petproject.Response

import com.mybclym.androidacademy.petproject.DataModel.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonGenre(
    val id: Int,
    @SerialName("name")
    val name: String
) {
    fun convert() = Genre(id, name)
}

@Serializable
class JsonMovieCredits(
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
    @SerialName("cast_id")
    val castId: Int
)

@Serializable
class Results(
    @SerialName("results")
    val results: List<JsonNowPlayingMoviesResponse>
)

@Serializable
class JsonNowPlayingMoviesResponse(
    @SerialName("id")
    val id: Int
)

@Serializable
class JsonMovie(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String?,
    @SerialName("backdrop_path")
    val backdropPicture: String?,
    @SerialName("runtime")
    val runtime: Int?,
    @SerialName("genres")
    val genres: List<JsonGenre>,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
    @SerialName("overview")
    val overview: String?,
    @SerialName("adult")
    val adult: Boolean
)

@Serializable
data class ConfigurationImages(
    @SerialName("secure_base_url")
    val imageUrl: String,
    @SerialName("backdrop_sizes")
    val backdropSize: List<String>,
    @SerialName("poster_sizes")
    val posterSize: List<String>
)

@Serializable
data class Configuration(
    @SerialName("images")
    val images: ConfigurationImages
)

