package com.mybclym.androidacademy.petproject.dataModel

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GenreConverter {
    @TypeConverter
    fun fromGenres(genres: List<GenreEntity>): String =
        Json.encodeToString(genres)

    @TypeConverter
    fun toGenres(json: String): List<GenreEntity> =
        Json.decodeFromString(json)
}