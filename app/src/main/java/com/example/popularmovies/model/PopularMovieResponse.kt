package com.example.popularmovies.model

data class PopularMovieResponse( // для работы api
    val page: Int,
    val results: List<Movie>
)
