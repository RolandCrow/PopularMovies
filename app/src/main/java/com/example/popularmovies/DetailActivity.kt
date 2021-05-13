package com.example.popularmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.popularmovies.model.Movie

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "movie" // константы
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w185/"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val titleText: TextView = findViewById(R.id.title_text) //  присоединяем id из активности
        val releaseText: TextView = findViewById(R.id.release_text)
        val overviewText: TextView = findViewById(R.id.overview_text)
        val poster: ImageView = findViewById(R.id.movie_poster)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE) // получаем через интент
        // информацию из дата класса и создаем объект и отобржаем его на экране
        movie?.run {
            titleText.text = title
            releaseText.text = release_date.take(4) // подгружаем 4 строчки
            overviewText.text = "Overview: $overview"


            Glide.with(this@DetailActivity) // используем Glide для загрузки изображения постера
        // фильма с сайта
                .load("$IMAGE_URL$poster_path") // откуда загружаем
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter() // куда ставим
                .into(poster)

        }


    }
}