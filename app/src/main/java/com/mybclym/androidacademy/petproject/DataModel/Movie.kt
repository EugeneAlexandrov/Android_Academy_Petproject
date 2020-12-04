package com.mybclym.androidacademy.petproject.DataModel

data class Movie(
    val id: Int,
    val title: String = "",
    val age: String = "",
    val genre: List<String> = emptyList(),
    val rating: Float = 0F,
    val reviewsCount: Int = 0,
    val storyLine: String = "",
    val actors: List<Actor> = emptyList(),
    val duration: Int = 0,
    val poster: String = ""
)