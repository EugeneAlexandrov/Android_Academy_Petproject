package com.mybclym.androidacademy.petproject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class FragmentMovieDetails : Fragment() {

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
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.back_btn_tv).apply {
            setOnClickListener {
                movieClickListener?.showMovieList()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        movieClickListener = null
    }
}