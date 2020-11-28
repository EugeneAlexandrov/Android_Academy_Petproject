package com.mybclym.androidacademy.petproject

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

    private var movieItemCardView: CardView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieItemCardView = view.findViewById<CardView>(R.id.movie_item).apply {
            setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, FragmentMovieDetails())
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
    }
}