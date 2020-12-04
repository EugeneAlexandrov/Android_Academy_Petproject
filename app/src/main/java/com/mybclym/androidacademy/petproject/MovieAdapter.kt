package com.mybclym.androidacademy.petproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mybclym.androidacademy.petproject.DataModel.Movie

//у адаптера есть ссылка на листенер, чтобы запустить фрагмент из активити
class MovieAdapter(val movieClickListener: OnMovieClickListener) :
    RecyclerView.Adapter<ItemMovieViewHolder>() {

    private var moviesList = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMovieViewHolder {
        return ItemMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemMovieViewHolder, position: Int) {
        //в адаптере есть список фильмов, передаем в холдер фильм по позиции
        holder.bind(moviesList[position], movieClickListener)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun setUpMoviesList(movies: List<Movie>) {
        moviesList = movies
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
    private val title: TextView? = itemView.findViewById(R.id.title)
    private val duration: TextView? = itemView.findViewById(R.id.duration_tv)

    fun bind(movie: Movie, onMovieClickListener: OnMovieClickListener) {
        Glide.with(itemView.context)
            .load(movie.poster)
            .apply(imageOption)
            .into(poster)

        itemView.setOnClickListener {
            //вызываем метод активити для обращения к FragmentManager
            onMovieClickListener.showDetails(movie.id)
        }

        ageRestriction?.text = movie.age
        reviews?.text = movie.reviewsCount.toString()
        genre?.text = movie.genre.joinToString()
        title?.text = movie.title
        duration?.text = movie.duration.toString()
    }

    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.no_image)
            .fallback(R.drawable.no_image)
            .fitCenter()
    }
}