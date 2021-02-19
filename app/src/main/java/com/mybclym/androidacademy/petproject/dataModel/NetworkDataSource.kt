package com.mybclym.androidacademy.petproject.dataModel

//interface NetworkDataSource {
//    suspend fun getMoviesAsync(): List<Movie>
//    suspend fun getMovieByIdAsync(movieId: Int): Movie?
//    suspend fun getConfig()
//}
//
//class NetworkDataSourceImpl(private val networkModule: NetworkModule) : NetworkDataSource {
//
//    lateinit var imageUrl: String
//    lateinit var backdropSize: List<String>
//    lateinit var posterSize: List<String>
//    var movieList: List<Movie> = emptyList()
//
//    override suspend fun getMoviesAsync(): List<Movie> =
//        if (movieList.isEmpty()) {
////            movieList = loadMovies()
//            movieList
//        } else movieList
//
////    override suspend fun getMovieByIdAsync(movieId: Int): Movie? =
////        withContext(Dispatchers.IO) {
////            val movie: Movie? = movieList.find { it.id == movieId }
////            movie?.actors = loadActors(movieId).cast.map { it ->
//////                Actor(
//////                    it.id,
//////                    it.name,
//////                    imageUrl + backdropSize[1] + it.profilePicture
//////                )
////            }
////            return@withContext movie
////        }
//
//    override suspend fun getConfig() {
//        val config = networkModule.movieApiService.getConfiguration()
//        imageUrl = config.images.imageUrl
//        posterSize = config.images.posterSize
//        backdropSize = config.images.backdropSize
//    }
//
//    private suspend fun loadDetails(id: Int): DetailsResponse =
//        networkModule.movieApiService.getMovieDetails(id)
//
//    private suspend fun loadActors(id: Int): CreditsResponse =
//        networkModule.movieApiService.getMovieActors(id)
//
////    private suspend fun loadMovies(): List<Movie> =
////        withContext(Dispatchers.IO) {
////            val movieIDsList: List<JsonNowPlayingResults> =
////                networkModule.movieApiService.getNowPlaying().results
////            val jsonMovieList: List<DetailsResponse> =
////                movieIDsList.map { loadDetails(it.id) }
////            jsonMovieList.map { jsonMovie ->
////                Movie(
////                    id = jsonMovie.id,
////                    title = jsonMovie.title,
////                    overview = jsonMovie.overview ?: "",
////                    poster = imageUrl + posterSize[3] + jsonMovie.posterPicture,
////                    backdrop = imageUrl + backdropSize[1] + jsonMovie.backdropPicture,
////                    release = jsonMovie.rel ?: 0,
////                    ratings = jsonMovie.ratings,
////                    numberOfRatings = jsonMovie.votesCount,
////                    minimumAge = if (jsonMovie.adult) 16 else 13,
////                    genres = jsonMovie.genres.map { it.convert() },
////                )
////            }
////        }
//}