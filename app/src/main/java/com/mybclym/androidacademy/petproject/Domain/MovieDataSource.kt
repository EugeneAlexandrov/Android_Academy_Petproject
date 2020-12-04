package com.mybclym.androidacademy.petproject.Domain

import com.mybclym.androidacademy.petproject.DataModel.Movie

object MovieDataSource {
    private var count = 0
    private val movieList = listOf(
        Movie(
            count++,
            "Мстители: Что-то там",
            "+13",
            listOf("Action", "Adventure", "Fantasy"),
            4.5F,
            135,
            "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\\' actions and restore balance to the universe.",
            emptyList(),
            180,
            ""
        ),
        Movie(
            count++,
            "Мстители: где Тони Старк умер",
            "+18",
            listOf("Action", "Comedy", "Fantasy"),
            3.5F,
            120,
            "Тони Страк того",
            emptyList(),
            120,
            ""
        ),
        Movie(count++),
        Movie(count++),
        Movie(count++),
        Movie(count++),
        Movie(count++),
        Movie(count++),
        Movie(count++),
        Movie(count++),
    )

    fun getMovies(): List<Movie> {
        return movieList
    }

    fun findMovieByID(id: Int): Movie {
        var result = Movie(0)
        for (movie in movieList) {
            if (movie.id == id)
                result = movie
        }
        return result
    }
}
