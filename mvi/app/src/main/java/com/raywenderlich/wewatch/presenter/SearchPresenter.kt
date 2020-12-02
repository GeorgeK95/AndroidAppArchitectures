package com.raywenderlich.wewatch.presenter

import com.raywenderlich.wewatch.data.MovieInteractor
import com.raywenderlich.wewatch.domain.MovieState
import com.raywenderlich.wewatch.view.AddView
import com.raywenderlich.wewatch.view.SearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SearchPresenter(private val movieInteractor: MovieInteractor) {

    private lateinit var view: SearchView
    private val compositeDisposable = CompositeDisposable()

    fun bind(searchView: SearchView) {
        this.view = searchView
        compositeDisposable.addAll(
            observeConfirmIntent(),
            observeAddMovieIntent(),
            observeMovieDisplayIntent()
        )
    }

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun observeConfirmIntent() = view.confirmIntent()
        .observeOn(Schedulers.io())
        .flatMap { movieInteractor.addMovie(it) }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { view.render(it) }

    private fun observeAddMovieIntent() = view.addMovieIntent()
        .map { MovieState.ConfirmationState(it) }
        .subscribe { view.render(it) }

    private fun observeMovieDisplayIntent() = view.displayMoviesIntent()
        .doOnNext { Timber.d("Intent: display movies") }
        .flatMap { movieInteractor.searchMovies(it) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .startWith { view.render(MovieState.LoadingState) }
        .subscribe { view.render(it) }
}
