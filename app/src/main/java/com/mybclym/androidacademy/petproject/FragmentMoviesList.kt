package com.mybclym.androidacademy.petproject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mybclym.androidacademy.petproject.DataModel.Movie
import com.mybclym.androidacademy.petproject.DataModel.loadMovies
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentMoviesList : BaseFragment() {
    // контекст фрагмента активити имплементит OnMovieClickListener
    private var movieClickListener: OnMovieClickListener? = null
    private var recycler: RecyclerView? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    private var movieList: MutableList<Movie> = mutableListOf()

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
        //если movieClickListener !=null создаем адаптер
        //поркидываем дальше лисенер
        val adapter = MovieAdapter(movieClickListener)
        recycler = view.findViewById(R.id.movie_list_rv)
        scope.launch {
            val movieDataSoutce=dataProvider?.dataSource()
            movieList = movieDataSoutce.getMoviesAsync()
            withContext(Dispatchers.Main) {
                adapter?.setUpMoviesList(movieList)
                recycler?.adapter = adapter
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        //отвязываем лисенер и ресайклер
        movieClickListener = null
        recycler = null
        scope.cancel()
    }
}

