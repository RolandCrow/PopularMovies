package com.example.popularmovies.api

import com.example.popularmovies.model.PopularMovieResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieService { // прямая работа c api через retrofit и RxJava

    @GET("movie/popular") // добавляется к Url получить список фильмов
    fun getPopularityMovies(@Query("api_key") apiKey: String): Observable<PopularMovieResponse>
// добавляем к запросу ключ апи , создаем поток для данных в респонс с дата классом



}