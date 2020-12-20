package com.mybclym.androidacademy.petproject

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mybclym.androidacademy.petproject.DataModel.Movie
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class FragmentMovieDetails : BaseFragment() {
    //фрагменту нужна ссылка на листенер, чтобы вернуться назад
    private var movieClickListener: OnMovieClickListener? = null
    private var movieId: Int = 0
    private var movie: Movie? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    private lateinit var poster: ImageView
    private lateinit var ageRestriction: TextView
    private lateinit var reviews: TextView
    private lateinit var genre: TextView
    private lateinit var title: TextView
    private lateinit var storyLine: TextView
    private var actorsRecyclerView: RecyclerView? = null
    private var actorAdapter: ActorAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMovieClickListener) {
            movieClickListener = context
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
        //id фильма пробрасывается от адаптера до активити и из активити в фрагмент
        val movieId = movieID() ?: 0
        findViews(view)
        loadMovie(movieId)
        view.findViewById<TextView>(R.id.back_btn_tv).apply {
            setOnClickListener {
                movieClickListener?.showMovieList()
            }
        }
    }

    override fun onDetach() {
        movieClickListener = null
        scope.cancel()
        actorsRecyclerView = null
        super.onDetach()
    }

    private fun findViews(view: View) {
        actorsRecyclerView = view.findViewById(R.id.actors_rv)
        actorAdapter = ActorAdapter()
        actorsRecyclerView?.adapter = actorAdapter
        actorsRecyclerView?.addItemDecoration(HorisontalSpaceItemDecoration())
        poster = view.findViewById(R.id.background_poster_iv)
        ageRestriction = view.findViewById(R.id.age_restrictions_tv)
        reviews = view.findViewById(R.id.reviews_count_tv)
        genre = view.findViewById(R.id.movie_genre_tv)
        title = view.findViewById(R.id.title_tv)
        storyLine = view.findViewById(R.id.storyline_tv)
    }

    private fun loadMovie(movieId: Int) {
        scope.launch {
            movie = dataProvider?.dataSource()?.getMovieByIdAsync(movieId)
            bindViews(movie)
        }
    }

    private suspend fun bindViews(movie: Movie?) {
        withContext(Dispatchers.Main) {
            actorAdapter?.setUpActorsList(movie?.actors)
            storyLine.text = movie?.overview
            ageRestriction.text = movie?.minimumAge.toString()
            reviews.text = movie?.numberOfRatings.toString()
            genre.text = movie?.let {
                it.genres.joinToString { genre -> genre.name }
            }
            title.text = movie?.title
            Glide.with(view?.context)
                .load(movie?.backdrop)
                .apply(imageOption)
                .into(poster)
        }
    }

    private fun movieID(): Int? =
        arguments?.let {
            it.getInt(PARAM_MOVIE_ID, 0)
        }

    companion object {
        private const val PARAM_MOVIE_ID = "movie_ID"

        private val imageOption = RequestOptions()
            .placeholder(R.drawable.no_image)
            .fallback(R.drawable.no_image)
            .fitCenter()

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

    private class HorisontalSpaceItemDecoration : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            if (parent.getChildAdapterPosition(view) != (parent.getAdapter()?.getItemCount()
                    ?: 0) - 1
            ) {
                outRect.right = 24
            }
        }
    }
}

