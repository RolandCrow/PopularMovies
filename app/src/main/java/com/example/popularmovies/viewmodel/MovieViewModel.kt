package com.example.popularmovies.viewmodel

import android.icu.util.Calendar
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies.api.MovieRepository
import com.example.popularmovies.model.Movie
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class MovieViewModel(private val movieRepository: MovieRepository): ViewModel() {

    private val popularMoviesLiveData = MutableLiveData<List<Movie>>() // добавляем фильмы вне
// основного потока

    private val errorLiveData = MutableLiveData<String>() // на случай ошибок

    val popularMovies: LiveData<List<Movie>>
    get() = popularMoviesLiveData

    val error: LiveData<String>
    get() = errorLiveData

    private var disposable = CompositeDisposable() // ранняя утилизация ненужных потоков



    fun fetchPopularMovies() { // вывод список фильмов во вью в основной поток из реактивного
    // потока api
        disposable.add(movieRepository.fetchMovies()
            .subscribeOn(Schedulers.io()) // меняем направление потока
            .flatMap { Observable.fromIterable(it.results) } // добавляем в поток фильтры
            .toList()     // получаем нужные данные из потока
            .observeOn(AndroidSchedulers.mainThread()) // выводим Rx поток на главный поток во вью
            .subscribe({
                popularMoviesLiveData.postValue(it)
            },{ error ->
                errorLiveData.postValue("An error occurred: ${error.message}")
                })
        )

    }


    override fun onCleared() {
        super.onCleared()
        disposable.dispose() // удаляем лишние потоки
    }
}