package com.mybclym.androidacademy.petproject.Views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.androidacademy.petproject.DataModel.Movie
import com.mybclym.androidacademy.petproject.ViewModels.MovieListViewModel
import com.mybclym.androidacademy.petproject.R

/**
 * A simple [Fragment] subclass.
 */
class FragmentMoviesList : BaseFragment() {
    // контекст фрагмента - активити, имплементит OnMovieClickListener
    private var movieClickListener: OnMovieClickListener? = null
    private var recycler: RecyclerView? = null
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: MovieListViewModel

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
        observeMoviesList()
    }

    private fun observeMoviesList() {
        viewModel = ViewModelProvider(
            this, dataProvider.viewModelFactory()
        ).get(MovieListViewModel::class.java)
        viewModel.loadMoviesList()
        viewModel.movieList.observe(this.viewLifecycleOwner, this::bindView)
    }

    private fun findView(view: View) {
        recycler = view.findViewById(R.id.movie_list_rv)
        //прокидываем дальше лисенер
        movieAdapter = MovieAdapter(movieClickListener)
        recycler?.adapter = movieAdapter
    }

    private fun bindView(movieList: List<Movie>) {
        movieAdapter.setUpMoviesList(movieList)
    }

    override fun onDetach() {
        //отвязываем лисенер и ресайклер
        movieClickListener = null
        recycler = null
        super.onDetach()
    }
}

