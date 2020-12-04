package com.mybclym.androidacademy.petproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

//имплементим листенер для проброса из фрагментов и адаптера
class MainActivity : AppCompatActivity(), OnMovieClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            initMovieListFragment()
        }
    }

    private fun initMovieListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                FragmentMoviesList(),
                TAG_FRAGMENT_LIST
            )
            .commit()
    }

    /*
    вызываем в адаптере через OnMovieClickListener()
    id берется из листа и позиции в холдере
    * */
    override fun showDetails(id: Int) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                FragmentMovieDetails.newInstance(id),
                TAG_FRAGMENT_DETAILS
            )
            .addToBackStack(null)
            .commit()
    }

    //вызываем в адаптере через OnMovieClickListener()
    override fun showMovieList() {
        this.onBackPressed()
    }

    companion object {
        const val TAG_FRAGMENT_LIST = "ListFragment"
        const val TAG_FRAGMENT_DETAILS = "DetailsFragment"
    }
}