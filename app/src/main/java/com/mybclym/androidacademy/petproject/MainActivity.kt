package com.mybclym.androidacademy.petproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity(), MovieClickListener {
    private var detailFragment: FragmentMovieDetails? = null
    private var listFragment: FragmentMoviesList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            initMovieListFragment()
        } else {
            listFragment =
                supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_LIST) as FragmentMoviesList
        }
    }

    private fun initMovieListFragment() {
        listFragment = FragmentMoviesList()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, listFragment!!, TAG_FRAGMENT_LIST)
            .addToBackStack(null)
            .commit()
    }

    override fun showDetails() {
        detailFragment = FragmentMovieDetails()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentMovieDetails(), TAG_FRAGMENT_DETAILS)
            .addToBackStack(null)
            .commit()
    }

    override fun showMovieList() {
        supportFragmentManager.popBackStack()
    }

    companion object {
        const val TAG_FRAGMENT_LIST = "ListFragment"
        const val TAG_FRAGMENT_DETAILS = "DetailsFragment"
    }
}