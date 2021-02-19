package com.mybclym.androidacademy.petproject.views

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mybclym.androidacademy.petproject.dataModel.domainModel.Movie
import com.mybclym.androidacademy.petproject.R
import com.mybclym.androidacademy.petproject.dataModel.domainModel.Actor
import com.mybclym.androidacademy.petproject.viewModels.MovieItemViewModel
import com.mybclym.androidacademy.petproject.viewModels.MoviesItemViewModelsFactory

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class FragmentMovieDetails : BaseFragment() {
    //фрагменту нужна ссылка на листенер, чтобы вернуться назад
    private var movieClickListener: OnMovieClickListener? = null
    private lateinit var movieItemViewModel: MovieItemViewModel

    private lateinit var backpath: ImageView
    private lateinit var ageRestriction: TextView
    private lateinit var reviews: TextView
    private lateinit var genre: TextView
    private lateinit var title: TextView
    private lateinit var storyLine: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var runtime: TextView
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
        findViews(view)
        movieItemViewModel = ViewModelProvider(
            this, MoviesItemViewModelsFactory(dataProvider.movieRepository(), movieID() ?: 0)
        ).get(MovieItemViewModel::class.java)
        movieItemViewModel.movieItem.observe(this.viewLifecycleOwner, this::bindViews)
        movieItemViewModel.details.observe(this.viewLifecycleOwner, this::bindDetails)
        movieItemViewModel.movieActors.observe(this.viewLifecycleOwner, this::updateActorsAdapter)
        movieItemViewModel.eventActorsNetworkError.observe(
            this.viewLifecycleOwner,
            this::onActorsNetworkError
        )
        movieItemViewModel.actorsLoading.observe(this.viewLifecycleOwner, this::setActorsLoading)
        view.findViewById<TextView>(R.id.back_btn_tv).setOnClickListener {
            movieClickListener?.showMovieList()
        }
    }

    private fun setActorsLoading(loading: Boolean) {
        if (loading) {
            progressBar.visibility = View.VISIBLE
            actorsRecyclerView?.visibility = View.INVISIBLE
        } else {
            progressBar.visibility = View.GONE
            actorsRecyclerView?.visibility = View.VISIBLE
        }
    }

    private fun bindDetails(details: Int) {
        runtime.text = details.toString()
    }

    private fun updateActorsAdapter(actorList: List<Actor>) {
        actorAdapter?.setUpActorsList(actorList)
    }

    override fun onDestroyView() {
        movieClickListener = null
        actorsRecyclerView = null
        super.onDestroyView()
    }

    private fun onActorsNetworkError(networkErrorShown: Boolean) {
        if (networkErrorShown) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            movieItemViewModel.onNetworkErrorShown()
        }
    }

    private fun findViews(view: View) {
        actorsRecyclerView = view.findViewById(R.id.actors_rv)
        actorAdapter = ActorAdapter()
        actorsRecyclerView?.adapter = actorAdapter
        actorsRecyclerView?.addItemDecoration(HorisontalSpaceItemDecoration())
        backpath = view.findViewById(R.id.background_poster_iv)
        ageRestriction = view.findViewById(R.id.age_restrictions_tv)
        reviews = view.findViewById(R.id.reviews_count_tv)
        genre = view.findViewById(R.id.movie_genre_tv)
        title = view.findViewById(R.id.title_tv)
        storyLine = view.findViewById(R.id.storyline_tv)
        progressBar = view.findViewById(R.id.actors_progress_bar)
        runtime = view.findViewById(R.id.runtime_tv)
    }

    private fun bindViews(movie: Movie) {
        storyLine.text = movie.overview
        ageRestriction.text = movie.minimumAge.toString()
        reviews.text = movie.numberOfRatings.toString()
        genre.text = movie.let {
            it.genres?.joinToString { genre -> genre.name }
        }
        title.text = movie.title
        backpath.load(movie.backdrop)
    }

    private fun movieID(): Int? =
        arguments?.getInt(PARAM_MOVIE_ID, 0)

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

    private class HorisontalSpaceItemDecoration : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            if (parent.getChildAdapterPosition(view) != (parent.adapter?.itemCount
                    ?: 0) - 1
            ) {
                outRect.right = 24
            }
        }
    }
}

