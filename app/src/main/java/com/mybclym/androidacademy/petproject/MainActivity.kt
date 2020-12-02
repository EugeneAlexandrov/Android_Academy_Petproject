package com.mybclym.androidacademy.petproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity(), MovieClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            initMovieListFragment()
        }
    }

    private fun initMovieListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentMoviesList(), TAG_FRAGMENT_LIST)
            .commit()
    }

    override fun showDetails() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentMovieDetails(), TAG_FRAGMENT_DETAILS)
            .addToBackStack(null)
            .commit()
    }

    override fun showMovieList() {
        this.onBackPressed()
    }

    companion object {
        const val TAG_FRAGMENT_LIST = "ListFragment"
        const val TAG_FRAGMENT_DETAILS = "DetailsFragment"
    }
}