package com.example.popularmovies.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popularmovies.R
import com.example.popularmovies.model.Movie

class MovieAdapter(private val clickListener: MovieClickListener): // адаптер для recyclerView
RecyclerView.Adapter<MovieAdapter.MovieViewHolder>()  {

    private val movies = mutableListOf<Movie>() // изменяемый список





    class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val imageUrl = "https://image.tmdb.org/t/p/w185/"

        private val titleText: TextView by lazy {
            itemView.findViewById(R.id.movie_title) // устанавливаем связь со вью, иницируем как
        // можно позже

        }

        private val poster: ImageView by lazy {
            itemView.findViewById(R.id.movie_poster)
        }

        fun bind(movie: Movie) {
            titleText.text = movie.title // устанавливаем текст

            Glide.with(itemView.context) // используем Glide для загрузки постера
                .load("$imageUrl${movie.poster_path}")
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(poster)

        }
    }

    interface MovieClickListener {
    fun onMovieClick(movie: Movie)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_movie_item, parent, false) // иницируем страницу с описанием
        // одного фильма
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
       val movie = movies[position] // выбор одного из фильмов
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            clickListener.onMovieClick(movie)
        }
    }

    override fun getItemCount() = movies.size

    fun addMovies(movieList: List<Movie>) { // добавляем фильм в массив

        movies.addAll(movieList)
        notifyItemRangeInserted(0, movieList.size)
    }

}