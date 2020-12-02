package com.raywenderlich.wewatch.presenter

import com.raywenderlich.wewatch.data.MovieInteractor
import com.raywenderlich.wewatch.domain.MovieState
import com.raywenderlich.wewatch.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainPresenter(private val movieInteractor: MovieInteractor) {

    private lateinit var view: MainView
    private val compositeDisposable = CompositeDisposable()

    fun bind(view: MainView) {
        this.view = view
        compositeDisposable.addAll(observeMovieDeleteIntent(), observeMovieDisplay())
    }

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun observeMovieDeleteIntent(): Disposable {
        return view.deleteMovieIntent()
            .doOnNext { Timber.d("Intent: delete movie") }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .flatMap { movieInteractor.deleteMovie(it) }
            .subscribe()

    }

    private fun observeMovieDisplay(): Disposable {
        return view.displayMoviesIntent()
            .doOnNext { Timber.d("Intent: display movie") }
            .flatMap { movieInteractor.getMovieList() }
            .startWith { view.render(MovieState.LoadingState) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view.render(it) }
    }

}
