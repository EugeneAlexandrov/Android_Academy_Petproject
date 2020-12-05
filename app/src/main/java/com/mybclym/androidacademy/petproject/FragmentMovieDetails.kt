package com.mybclym.androidacademy.petproject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.androidacademy.petproject.DataModel.Movie
import com.mybclym.androidacademy.petproject.Domain.MovieDataSource

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class FragmentMovieDetails : Fragment() {
    //фрагменту нужна ссылка на листенер, чтобы вернуться назад
    private var movieClickListener: OnMovieClickListener? = null
    private var movieId: Int = 0
    private lateinit var movie: Movie

    private lateinit var poster: ImageView
    private lateinit var ageRestriction: TextView
    private lateinit var reviews: TextView
    private lateinit var genre: TextView
    private lateinit var title: TextView
    private lateinit var storyLine: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMovieClickListener) {
            movieClickListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //id фильма пробрасывается от адаптера до активити и из активити в фрагмент
        arguments?.let {
            movieId = it.getInt(PARAM_MOVIE_ID, 0)
            movie = MovieDataSource.findMovieByID(movieId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.back_btn_tv).apply {
            setOnClickListener {
                movieClickListener?.showMovieList()
            }
        }
        poster = view.findViewById(R.id.background_poster_iv)
        ageRestriction = view.findViewById(R.id.age_restrictions_tv)
        ageRestriction.text = movie.age
        reviews = view.findViewById(R.id.reviews_count_tv)
        reviews.text = movie.reviewsCount.toString()
        genre = view.findViewById(R.id.movie_genre_tv)
        genre.text = movie.genre.joinToString()
        title = view.findViewById(R.id.title_tv)
        title.text = movie.title
        storyLine = view.findViewById(R.id.storyline_tv)
        storyLine.text = movie.storyLine
    }

    override fun onDetach() {
        super.onDetach()
        movieClickListener = null
    }

    companion object {
        private const val PARAM_MOVIE_ID = "movie_ID"

        //в активити вызывается фрагмент с параметром
        fun newInstance(
            movieID: Int,
        ): FragmentMovieDetails {
            val fragment = FragmentMovieDetails()
            val args = Bundle()
            args.putInt(PARAM_MOVIE_ID, movieID)
            fragment.arguments = args
            return fragment
        }
    }
}

