package com.mybclym.androidacademy.petproject.views

import android.content.Context
import androidx.fragment.app.Fragment
import com.mybclym.androidacademy.petproject.DataProvider

open class BaseFragment : Fragment() {
    lateinit var dataProvider: DataProvider

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appContext = context.applicationContext
        if (appContext is DataProvider) {
            dataProvider = appContext
        }
    }
}