package com.mybclym.androidacademy.petproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.androidacademy.petproject.DataModel.Movie
import com.mybclym.androidacademy.petproject.DataModel.loadMovies
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentMoviesList : BaseFragment() {
    // контекст фрагмента - активити, имплементит OnMovieClickListener
    private var movieClickListener: OnMovieClickListener? = null
    private var recycler: RecyclerView? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var movieList: List<Movie>
    private var moviesDataSource: MoviesDataSource? = null
    private lateinit var movieAdapter: MovieAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMovieClickListener) {
            //инициализируем лисенер
            movieClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findView(view)
        loadMoviesList()
    }

    private fun findView(view: View) {
        recycler = view.findViewById(R.id.movie_list_rv)
        //прокидываем дальше лисенер
        movieAdapter = MovieAdapter(movieClickListener)
        recycler?.adapter = movieAdapter
    }

    private fun loadMoviesList() {
        scope.launch {
            movieList = dataProvider?.dataSource()?.getMoviesAsync() ?: emptyList()
            bindView()
        }
    }

    private suspend fun bindView() {
        withContext(Dispatchers.Main) {
            movieAdapter.setUpMoviesList(movieList)
        }
    }

    override fun onDetach() {
        //отвязываем лисенер и ресайклер
        moviesDataSource = null
        movieClickListener = null
        recycler = null
        scope.cancel()
        super.onDetach()
    }
}

