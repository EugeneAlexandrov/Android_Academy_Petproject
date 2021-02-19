package com.mybclym.androidacademy.petproject.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mybclym.androidacademy.petproject.dataModel.domainModel.Movie
import com.mybclym.androidacademy.petproject.R

//у адаптера есть ссылка на листенер, чтобы запустить фрагмент из активити
class MovieAdapter(private val movieClickListener: OnMovieClickListener?) :
    RecyclerView.Adapter<ItemMovieViewHolder>() {

    private var moviesList = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMovieViewHolder {
        return ItemMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemMovieViewHolder, position: Int) {
        //в адаптере есть список фильмов, передаем в холдер фильм по позиции
        holder.bind(moviesList[position])
        holder.itemView.setOnClickListener {
            movieClickListener?.showDetails(moviesList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun setUpMoviesList(movies: List<Movie>) {
        moviesList = movies
        notifyDataSetChanged()
    }
}

interface OnMovieClickListener {
    fun showDetails(id: Int)
    fun showMovieList()
}

class ItemMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val poster: ImageView? = itemView.findViewById(R.id.background_poster_iv)
    private val ageRestriction: TextView? = itemView.findViewById(R.id.age_restrictions_tv)
    private val reviews: TextView? = itemView.findViewById(R.id.reviews_count_tv)
    private val genre: TextView? = itemView.findViewById(R.id.movie_genre_tv)
    private val title: TextView? = itemView.findViewById(R.id.title_tv)
    private val release: TextView? = itemView.findViewById(R.id.release_tv)

    fun bind(movie: Movie) {
        ageRestriction?.text =
            itemView.context.getString(R.string.age_restriction, movie.minimumAge)
        reviews?.text = movie.numberOfRatings.toString()
        genre?.text = movie.genres?.joinToString { it.name }
        title?.text = movie.title
        release?.text = movie.release.toString()
        poster?.load(movie.poster) {
            crossfade(true)
            placeholder(R.drawable.no_image)
        }
    }
}