package com.raywenderlich.wewatch.presenter

import com.raywenderlich.wewatch.data.MovieInteractor
import com.raywenderlich.wewatch.domain.MovieState
import com.raywenderlich.wewatch.view.AddView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AddPresenter(private val movieInteractor: MovieInteractor) {

    private lateinit var view: AddView
    private val compositeDisposable = CompositeDisposable()

    fun bind(addView: AddView) {
        this.view = addView
        compositeDisposable.addAll(observeAddMovieIntent())
    }

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun observeAddMovieIntent() = view.addMovieIntent()
        .observeOn(Schedulers.io())
        .flatMap { movieInteractor.addMovie(it) }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { view.render(it) }

}