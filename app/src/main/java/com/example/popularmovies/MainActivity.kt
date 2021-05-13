package com.example.popularmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.adapter.MovieAdapter
import com.example.popularmovies.api.MovieApplication
import com.example.popularmovies.model.Movie
import com.example.popularmovies.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private val movieAdapter by lazy { // добавляем адаптер при клике на фильм, будет происходит
        // переход на его личную страницу
        MovieAdapter(object : MovieAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                openMoviesDetail(movie)
            }
        })
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.movie_list) // присоединяем ресайклер
    // вью со списком фильмов

        recyclerView.adapter = movieAdapter
        getMovies() // иницируем получение списка фильмов

    }



    @Suppress("UNCHECKED_CAST")
    private fun getMovies() {
        val movieRepository = (application as MovieApplication).movieRepository // получаем
    // поток данных с апи и проводим их через репозиторий в основной поток на главный экран

        val movieViewModel = ViewModelProvider(this, object :
                    ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T { // формируем список
                // фильмов
                return MovieViewModel(movieRepository) as  T
            }
                    }
        ).get(MovieViewModel::class.java)

        movieViewModel.fetchPopularMovies() // включаем поток
        movieViewModel.popularMovies
            .observe(this, { popularMovies -> // создаем поток с данными для последующего вывода
                movieAdapter.addMovies(popularMovies)

            })
        movieViewModel.error.observe(this, { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show() // показывем ошибки если есть
        })

    }

    private fun openMoviesDetail(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java).apply {  // при клике переходим к
            // описанию фильма, перед этим храня в интенте о нем данные, чтобы загрузился
            // определенный
            // выбранный фильм
            putExtra(DetailActivity.EXTRA_MOVIE, movie)
        }
        startActivity(intent)
    }
}