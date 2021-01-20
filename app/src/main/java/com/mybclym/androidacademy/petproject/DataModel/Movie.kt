package com.mybclym.androidacademy.petproject.DataModel

data class Movie(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val poster: String = "",
    val backdrop: String = "",
    val ratings: Float = 0f,
    val numberOfRatings: Int = 0,
    val minimumAge: Int = 0,
    val runtime: Int = 0,
    var genres: List<Genre> = emptyList(),
    var actors: List<Actor> = emptyList()
)