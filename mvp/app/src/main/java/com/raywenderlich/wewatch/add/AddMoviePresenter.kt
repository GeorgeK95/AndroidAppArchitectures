package com.raywenderlich.wewatch.add

import android.text.TextUtils
import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.Movie

class AddMoviePresenter(private val view: AddMovieContract.ViewInterface, private val dataSource: LocalDataSource) : AddMovieContract.PresenterInterface {

    override fun addMovie(title: String, releaseDate: String, posterPath: String) {
        if (com.raywenderlich.wewatch.util.TextUtils.isEmpty(title)) {
            view.displayError("Movie title cannot be empty")
        } else {
            val movie = Movie(title = title, releaseDate = releaseDate, posterPath = posterPath)
            dataSource.insert(movie)
            view.returnToMain()
        }
    }

}