package com.example.popularmovies.api

import android.app.Application
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieApplication: Application() { // репозиторий для работы с сетью

    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder() // подключаем ретрофит для работы с сетью
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create()) // используем моши для конверта
    // json
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // подключаем Rx для
    // обработки
            .build()
        val movieService = retrofit.create(MovieService::class.java)
        movieRepository = MovieRepository(movieService)
    }
}