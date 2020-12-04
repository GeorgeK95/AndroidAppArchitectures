package com.raywenderlich.wewatch.main

import android.util.Log
import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val view: MainContract.ViewInterface, private val dataSource: LocalDataSource) : MainContract.PresenterInterface {

    private val TAG = "MainPresenter"

    private val myMoviesObservable: Observable<List<Movie>>
        get() = dataSource.allMovies

    private val compositeDisposable = CompositeDisposable()

    private val observer: DisposableObserver<List<Movie>>
        get() = object : DisposableObserver<List<Movie>>() {

            override fun onNext(movieList: List<Movie>) {
                com.raywenderlich.wewatch.util.Log.d(TAG, "onNext")
                if (movieList.isEmpty()) {
                    view.displayNoMovies()
                } else {
                    view.displayMovies(movieList)
                }
            }

            override fun onError(@NonNull e: Throwable) {
                com.raywenderlich.wewatch.util.Log.d(TAG, "Error$e")
                e.printStackTrace()
                view.displayError("Error fetching movie list")
            }

            override fun onComplete() {
                com.raywenderlich.wewatch.util.Log.d(TAG, "Completed")
            }
        }

    override fun getMyMoviesList() {
        val myMoviesDisposable = myMoviesObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)

        compositeDisposable.add(myMoviesDisposable)
    }

    override fun onDeleteTapped(movieList: Set<*>) {
        movieList.forEach { dataSource.delete(it as Movie) }
        if (movieList.size == 1) {
            view.displayMessage("Movie deleted")
        } else if (movieList.size > 1) {
            view.displayMessage("Movies deleted")
        }
    }

    override fun onStop() {
        compositeDisposable.clear()
    }
}