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
import com.mybclym.androidacademy.petproject.Domain.MovieDataSource

/**
 * A simple [Fragment] subclass.
 */
class FragmentMoviesList : Fragment() {
    // контекст фрагмента активити имплементит OnMovieClickListener
    private var movieClickListener: OnMovieClickListener? = null
    private var recycler: RecyclerView? = null


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
        val adapter = movieClickListener?.let { MovieAdapter(it) }
        adapter?.setUpMoviesList(MovieDataSource.getMovies())
        recycler = view.findViewById(R.id.movie_list_rv)
        recycler?.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        //отвязываем лисенер и ресайклер
        movieClickListener = null
        recycler = null
    }
}

