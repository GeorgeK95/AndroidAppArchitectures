package com.raywenderlich.wewatch.presenter

import com.raywenderlich.wewatch.AddContract
import com.raywenderlich.wewatch.data.entity.Movie
import com.raywenderlich.wewatch.view.activities.MainActivity
import com.raywenderlich.wewatch.view.activities.SearchMovieActivity
import ru.terrakok.cicerone.Router

class AddMoviePresenter(
        private var view: AddContract.View?,
        private var interactor: AddContract.Interactor?,
        private val router: Router?) : AddContract.Presenter {

    override fun addMovies(title: String, year: String) {
        if (title.isNotBlank()) {
            interactor?.addMovie(Movie(title = title, releaseDate = year))
            router?.navigateTo(MainActivity.TAG)
        } else {
            view?.showMessage("You must enter a title")
        }
    }

    override fun searchMovies(title: String) {
        if (title.isNotBlank()) {
            router?.navigateTo(SearchMovieActivity.TAG)
        } else {
            view?.showMessage("You must enter a title")
        }
    }

    override fun onDestroy() {
        view = null
        interactor = null
    }
}