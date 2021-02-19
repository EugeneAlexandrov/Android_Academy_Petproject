package com.mybclym.androidacademy.petproject.dataModel.database

object MoviesDbContract {
    const val DATABASE_NAME = "Movies Database"

    object Movie {
        const val TABLE_NAME = "movies"

        const val COLUMN_NAME_ID = "movieId"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_POSTER = "poster"
        const val COLUMN_NAME_BACKDROP = "backdrop"
        const val COLUMN_NAME_RATING = "rating"
        const val COLUMN_NAME_NUMBER_OF_RATING = "numberOfRating"
        const val COLUMN_NAME_RELEASE_DATE = "release"
        const val COLUMN_NAME_ADULT = "adult"
    }

    object Genre {
        const val TABLE_NAME = "genres"

        const val COLUMN_NAME_GENRE_ID = "genreId"
        const val COLUMN_NAME_NAME = "genreName"
    }

    object Details {
        const val TABLE_NAME = "details"

        const val COLUMN_NAME_DETAILS_ID = "detailsId"
        const val COLUMN_NAME_ACTORS = "actors"
        const val COLUMN_NAME_RUNTIME = "runtime"
    }

    object Actor {
        const val TABLE_NAME = "actors"

        const val COLUMN_NAME_ID = "actorId"
        const val COLUMN_NAME_CREDIT_ID = "creditId"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_PICTURE = "picture"
        const val COLUMN_NAME_CHARACTER = "character"
    }
}