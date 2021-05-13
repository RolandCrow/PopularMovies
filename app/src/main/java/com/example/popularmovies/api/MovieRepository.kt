package com.example.popularmovies.api

class MovieRepository(private val movieService: MovieService) { // репозиторий из которого
// берется информация для запроса
    private val apiKey = "547e3b91ee141deea77769461b8a42b7"

    fun fetchMovies() = movieService.getPopularityMovies(apiKey)





}