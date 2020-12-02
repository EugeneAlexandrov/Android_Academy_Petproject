package com.mybclym.androidacademy.petproject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView

/**
 * A simple [Fragment] subclass.
 */
class FragmentMoviesList : Fragment() {

    private var movieClickListener: MovieClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieClickListener) {
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
        view.findViewById<CardView>(R.id.movie_item).apply {
            setOnClickListener {
                movieClickListener?.showDetails()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        movieClickListener = null
    }
}

interface MovieClickListener {
    fun showDetails()
    fun showMovieList()
}

