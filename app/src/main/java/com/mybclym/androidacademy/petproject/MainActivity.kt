package com.mybclym.androidacademy.petproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    private var moviesListFragment: FragmentMoviesList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            moviesListFragment = FragmentMoviesList()
            moviesListFragment?.apply {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, this, FRAGMENT_LIST_TAG)
                    .commit()
            }
        } else moviesListFragment =
            supportFragmentManager.findFragmentByTag(FRAGMENT_LIST_TAG) as? FragmentMoviesList
    }

    companion object {
        const val FRAGMENT_LIST_TAG = "LIST_FRAGMENT"
    }
}